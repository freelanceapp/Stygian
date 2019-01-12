package infobite.technology.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import infobite.technology.stygian.PayPal.PaypalActivity;
import infobite.technology.stygian.R;
import infobite.technology.stygian.activity.MainActivity;
import infobite.technology.stygian.activity.ThankyouActivity;
import infobite.technology.stygian.adapter.AdapterCart;
import infobite.technology.stygian.adapter.AdapterConfirmation;
import infobite.technology.stygian.database.HelperManager;
import infobite.technology.stygian.model.ProductDetail;
import infobite.technology.stygian.retrofit.ApInterface;
import infobite.technology.stygian.retrofit.response.LineItem;
import infobite.technology.stygian.retrofit.response.PostOrder;
import infobite.technology.stygian.util.ConnectionDetector;
import infobite.technology.stygian.util.SessionManager;
import infobite.technology.stygian.util.Utility;
import infobite.technology.stygian.util.WebApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("ValidFragment")
public class ConfirmationFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    Context ctx;
    RecyclerView recyclerView;
    TextView total_tv;
    LinearLayout ordernow_ll;
    public static String Payment_Package = "";
    SessionManager sessionManager;
    HelperManager helperManager;
    ConnectionDetector connectionDetector;

    @SuppressLint("ValidFragment")
    public ConfirmationFragment(Context ctx) {
        this.ctx = ctx;
        sessionManager = new SessionManager(ctx);
        helperManager = new HelperManager(ctx);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_confirmation, null);
        initXml(view);
        setOrder();
        return view;
    }

    private void setOrder() {

        ArrayList<ProductDetail> orderlist = helperManager.readAllCart();
        AdapterConfirmation adapter = new AdapterConfirmation(orderlist, ctx);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initXml(View view) {

        ordernow_ll = view.findViewById(R.id.ll_conforder_ordernow);
        recyclerView = view.findViewById(R.id.rv_conforder_recycler);
        total_tv = view.findViewById(R.id.tv_confirmation_total);

        ordernow_ll.setOnClickListener(this);
        total_tv.setText(Utility.getTotal(helperManager));
        Payment_Package = Utility.getTotal(helperManager);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_conforder_ordernow:
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

        String name = sessionManager.getData(SessionManager.KEY_ORDER_NAME);
        String mobile = sessionManager.getData(SessionManager.KEY_ORDER_MOBILE);
        String address = sessionManager.getData(SessionManager.KEY_ORDER_ADDRESS);
        String city = sessionManager.getData(SessionManager.KEY_ORDER_CITY);
        String state = sessionManager.getData(SessionManager.KEY_ORDER_STATE);
        String country = sessionManager.getData(SessionManager.KEY_ORDER_COUNTRY);
        String code = sessionManager.getData(SessionManager.KEY_ORDER_ZIPCODE);
        String paytype = sessionManager.getData(SessionManager.KEY_PAYMENT_TYPE);

        if (name.equals("") || mobile.equals("") || address.equals("") || city.equals("") ||
                state.equals("") || country.equals("") || code.equals("")) {
            Utility.toastView(ctx, "Please enter Your all shipping Details");
        } else if (paytype.equals("")) {
            Utility.toastView(ctx, "Please Select your payment method");
        } else {
            sessionManager.setData(SessionManager.KEY_ORDER_NAME, "");
            sessionManager.setData(SessionManager.KEY_ORDER_MOBILE, "");
            sessionManager.setData(SessionManager.KEY_ORDER_ADDRESS, "");
            sessionManager.setData(SessionManager.KEY_ORDER_CITY, "");
            sessionManager.setData(SessionManager.KEY_ORDER_STATE, "");
            sessionManager.setData(SessionManager.KEY_ORDER_COUNTRY, "");
            sessionManager.setData(SessionManager.KEY_ORDER_ZIPCODE, "");
            sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "");
            submitData(name, mobile, address, city, state, country, code, paytype, "");
        }
    }

    private void submitData(String name, String mobile, String address, String city, String state, String country, String code, String paytype, String txnid) {

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("total", Utility.getTotal(helperManager));
            paramObject.put("customer_id", sessionManager.getData(SessionManager.KEY_ID));

            JSONObject billing_obj = new JSONObject();
            billing_obj.put("first_name", name);
            billing_obj.put("last_name", "");
            billing_obj.put("company", "");
            billing_obj.put("address_1", address);
            billing_obj.put("address_2", "");
            billing_obj.put("city", city);
            billing_obj.put("state", state);
            billing_obj.put("postcode", code);
            billing_obj.put("country", country);
            billing_obj.put("email", sessionManager.getData(SessionManager.KEY_EMAIL));
            billing_obj.put("phone", mobile);
            paramObject.put("billing", billing_obj);

            paramObject.put("payment_method", paytype);
            paramObject.put("payment_method_title", "");
            paramObject.put("transaction_id", txnid);

            JSONArray line_item_array = new JSONArray();
            ArrayList<ProductDetail> list = helperManager.readAllCart();
            for (int i = 0; i < list.size(); i++) {

                float tot = list.get(i).getQuantity() * Float.parseFloat(list.get(i).getPrice());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("product_id", Integer.parseInt(list.get(i).getId()));
                jsonObject.put("variation_id", 0);
                jsonObject.put("quantity", list.get(i).getQuantity());
                jsonObject.put("sku", "");
                jsonObject.put("total", String.valueOf(tot));
                line_item_array.put(jsonObject);
            }
            paramObject.put("line_items", line_item_array);
            String data = paramObject.toString();
            submitdata(paramObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // post data
    private void submitdata(JSONObject paramObject) {
        Utility.showLoader(ctx);
        AndroidNetworking.post(WebApi.BASE_URL + WebApi.API_SUBMIT_ORDER)
                .addJSONObjectBody(paramObject)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, "your order has been successfully done");
                        helperManager.deleteallCart();
                        clear();
                        //startActivity(new Intent(ctx, ThankyouActivity.class));
                        Intent i = new Intent(getActivity(),PaypalActivity.class);
                        getActivity().startActivity(i);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

    private void clear() {
        sessionManager.setData(SessionManager.KEY_ORDER_NAME, "");
        sessionManager.setData(SessionManager.KEY_ORDER_MOBILE, "");
        sessionManager.setData(SessionManager.KEY_ORDER_ADDRESS, "");
        sessionManager.setData(SessionManager.KEY_ORDER_CITY, "");
        sessionManager.setData(SessionManager.KEY_ORDER_STATE, "");
        sessionManager.setData(SessionManager.KEY_ORDER_COUNTRY, "");
        sessionManager.setData(SessionManager.KEY_ORDER_ZIPCODE, "");
        sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "");
    }
}
