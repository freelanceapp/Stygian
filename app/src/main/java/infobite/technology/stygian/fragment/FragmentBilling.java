package infobite.technology.stygian.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import infobite.technology.stygian.R;
import infobite.technology.stygian.activity.MainActivity;
import infobite.technology.stygian.database.HelperManager;
import infobite.technology.stygian.model.ProductDetail;
import infobite.technology.stygian.retrofit.ApInterface;
import infobite.technology.stygian.retrofit.response.PostOrder;
import infobite.technology.stygian.util.ConnectionDetector;
import infobite.technology.stygian.util.ConstantData;
import infobite.technology.stygian.util.SessionManager;
import infobite.technology.stygian.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("ValidFragment")
public class FragmentBilling extends Fragment implements View.OnClickListener, Callback<PostOrder>
{
    Context ctx;
    EditText fname_et, lname_et, email_et, mobile_et, state_et, city_et, country_et, address_et;
    TextView total_tv;
    Button next_bt;

    HelperManager helperManager;
    ConnectionDetector connectionDetector;
    SessionManager sessionManager;
    Activity activity;

    String fname, lname, email, mobile, addres, city, state, country;

    public FragmentBilling(Context ctx, Activity activity) {
        this.ctx = ctx;
        this.activity = activity;
        helperManager = new HelperManager(ctx);
        sessionManager = new SessionManager(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billing_details, container, false);
        MainActivity.tooltext_tv.setText(ConstantData.BILLING);
        initXml(view);
        return view;
    }

    private void initXml(View view) {
        fname_et = view.findViewById(R.id.et_billing_fname);
        lname_et = view.findViewById(R.id.et_billing_lname);
        email_et = view.findViewById(R.id.et_billing_email);
        mobile_et = view.findViewById(R.id.et_billing_mobile);
        state_et = view.findViewById(R.id.et_billing_state);
        city_et = view.findViewById(R.id.et_billing_city);
        country_et = view.findViewById(R.id.et_billing_country);
        address_et = view.findViewById(R.id.et_billing_address);
        total_tv = view.findViewById(R.id.tv_billing_total);
        next_bt = view.findViewById(R.id.bt_billing_next);

        setTotal();
        next_bt.setOnClickListener(this);
    }

    public void setTotal() {
        float total = 0;
        ArrayList<ProductDetail> total_list = helperManager.readAllCart();
        for (int i = 0; i < total_list.size(); i++) {
            float pr = Float.parseFloat(total_list.get(i).getPrice());
            int qty = total_list.get(i).getQuantity();

            float tot = pr * qty;
            total += tot;
        }
        total_tv.setText("Total Order :        " + total);
    }

    public String getTotal() {
        float total = 0;
        ArrayList<ProductDetail> total_list = helperManager.readAllCart();
        for (int i = 0; i < total_list.size(); i++) {
            float pr = Float.parseFloat(total_list.get(i).getPrice());
            int qty = total_list.get(i).getQuantity();

            float tot = pr * qty;
            total += tot;
        }
        return String.valueOf(total);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_billing_next:
                boolean internet = connectionDetector.isConnected();
                if (internet) {
                    getData();
                } else {
                    Utility.toastView(ctx, ctx.getString(R.string.no_internet));
                }
                break;
        }
    }

    private void getData() {

        fname = fname_et.getText().toString();
        lname = lname_et.getText().toString();
        email = email_et.getText().toString();
        mobile = mobile_et.getText().toString();
        addres = address_et.getText().toString();
        city = city_et.getText().toString();
        state = state_et.getText().toString();
        country = country_et.getText().toString();

        if (fname.equals("") || lname.equals("") || email.equals("") || mobile.equals("") ||
                addres.equals("") || city.equals("") || state.equals("") || country.equals("")) {
            Utility.toastView(ctx, "Enter all details");
        } else {
            dialogPay();
        }
    }

    // payu money
    private void dialogPay() {
        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_payment_via);

        TextView online_tv = dialog.findViewById(R.id.tv_dialogpay_online);
        TextView cod_tv = dialog.findViewById(R.id.tv_dialogpay_cod);
        Button cancel_bt = dialog.findViewById(R.id.bt_dialogpay_cancel);

        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        online_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                startPayment();
            }
        });
        cod_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData("COD", "");
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void submitData(String pay_type, String txnid) {

        Utility.showLoader(ctx);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApInterface.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApInterface apiInterface = retrofit.create(ApInterface.class);
        // prepare call in Retrofit 2.0
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("customer_id", sessionManager.getData(SessionManager.KEY_ID));
            paramObject.put("payment_method", pay_type);
            paramObject.put("payment_method_title", "");
            paramObject.put("total", getTotal());
            paramObject.put("transaction_id", txnid);

            JSONObject billing_obj = new JSONObject();
            billing_obj.put("address_1", addres);
            billing_obj.put("address_2", "");
            billing_obj.put("city", city);
            billing_obj.put("company", "");
            billing_obj.put("country", country);
            billing_obj.put("email", email);
            billing_obj.put("first_name", fname);
            billing_obj.put("last_name", lname);
            billing_obj.put("phone", mobile);
            billing_obj.put("postcode", "");
            billing_obj.put("state", state);

            paramObject.put("billing", billing_obj);

            JSONArray line_item_array = new JSONArray();
            ArrayList<ProductDetail> list = helperManager.readAllCart();
            for (int i = 0; i < list.size(); i++) {

                float tot = list.get(i).getQuantity() * Float.parseFloat(list.get(i).getPrice());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("product_id", list.get(i).getId());
                jsonObject.put("quantity", list.get(i).getQuantity());
                jsonObject.put("sku", "");
                jsonObject.put("total", String.valueOf(tot));
                jsonObject.put("variation_id", "01");
                line_item_array.put(jsonObject);
            }
            paramObject.put("line_items", line_item_array);
            String data = paramObject.toString();
            Call<PostOrder> userCall = apiInterface.postOrderData(paramObject.toString());
            userCall.enqueue(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Call<PostOrder> call, Response<PostOrder> response) {
        Utility.hideLoader();
        Utility.toastView(ctx, "success");
        String data = response.toString();
        helperManager.deleteallCart();
        ((MainActivity) ctx).finishAffinity();
        ctx.startActivity(new Intent(ctx, MainActivity.class));
    }

    @Override
    public void onFailure(Call<PostOrder> call, Throwable t) {
        Utility.hideLoader();
        Utility.toastView(ctx, "failed");
    }
}
