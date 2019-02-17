package com.infobite.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.infobite.stygian.PayPal.PaypalActivity;
import com.infobite.stygian.R;
import com.infobite.stygian.adapter.AdapterConfirmation;
import com.infobite.stygian.model.ProductDetail;
import com.infobite.stygian.database.DatabaseHandler;
import com.infobite.stygian.util.ConnectionDetector;
import com.infobite.stygian.util.SessionManager;
import com.infobite.stygian.util.Utility;
import com.infobite.stygian.util.WebApi;

import static android.content.Context.MODE_PRIVATE;
import static com.infobite.stygian.activity.SplashActivity.mypreference;

@SuppressLint("ValidFragment")
public class ConfirmationFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    Context ctx;
    RecyclerView recyclerView;
    TextView total_tv,tv_payment;
    LinearLayout ordernow_ll;
    public static String Payment_Package = "";
    SessionManager sessionManager;
    public DatabaseHandler databaseCart;
    private String DATABASE_CART = "cart.db";
    ConnectionDetector connectionDetector;
    String totalAmount1 = "0";
    String Offer_Amount = "0";
    @SuppressLint("ValidFragment")
    public ConfirmationFragment(Context ctx) {
        this.ctx = ctx;
        sessionManager = new SessionManager(ctx);
        databaseCart = new DatabaseHandler(ctx, DATABASE_CART);
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

        ArrayList<ProductDetail> orderlist = databaseCart.getAllUrlList();
        AdapterConfirmation adapter = new AdapterConfirmation(orderlist, ctx);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initXml(View view) {
        //cart_count = AppPreference.getIntegerPreference(ctx, Constant.TOTAL_AMOUNT); //0 is the default value.

       /* SharedPreferences prefs = getActivity().getSharedPreferences(Constant.TOTAL_AMOUNT, MODE_PRIVATE);
        totalAmount = prefs.getString("Total", "0");//"No name defined" is the default value.
        Offer_Amount = prefs.getString("Offer", "0");//"No name defined" is the default value.
        Log.e("Total ",".."+totalAmount);*/

       /* totalAmount = sessionManager.getData(SessionManager.KEY_ORDER_PRICE);
        Log.e("total ",".."+totalAmount);*/

        SharedPreferences prefs = getActivity().getSharedPreferences(mypreference, MODE_PRIVATE);
        totalAmount1 = prefs.getString("total_price", "0");//"No name defined" is the default value.

        ordernow_ll = view.findViewById(R.id.ll_conforder_ordernow);
        recyclerView = view.findViewById(R.id.rv_conforder_recycler);
        total_tv = view.findViewById(R.id.tv_confirmation_total);
        tv_payment = view.findViewById(R.id.tv_payment);

        ordernow_ll.setOnClickListener(this);
        total_tv.setText(totalAmount1);
        tv_payment.setText(totalAmount1);

        /*total_tv.setText(Utility.getCartTotal(databaseCart));
        Payment_Package = Utility.getCartTotal(databaseCart);*/
        Payment_Package = totalAmount1;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_conforder_ordernow:
                boolean internet = connectionDetector.isConnected();
                if (internet) {
                   // getData();
                    Intent i = new Intent(getActivity(), PaypalActivity.class);
                    getActivity().startActivity(i);
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

        if (name.equals("") || mobile.equals("") || address.equals("") || city.equals("") ) {
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
            paramObject.put("total", totalAmount1);
           // paramObject.put("total", Utility.getCartTotal(databaseCart));
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
            ArrayList<ProductDetail> list = databaseCart.getAllUrlList();
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
            JSONArray line_item_array1 = new JSONArray();
                //float tot = list1.get(i).getQuantity() * Float.parseFloat(list1.get(i).getPrice());
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id", "1");
                jsonObject1.put("code", "ABC");
                jsonObject1.put("discount", "30");
                line_item_array1.put(jsonObject1);

            paramObject.put("coupon_lines", line_item_array1);
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
                        databaseCart.deleteallCart(databaseCart);
                        clear();
                        //startActivity(new Intent(ctx, ThankyouActivity.class));
                        Intent i = new Intent(getActivity(), PaypalActivity.class);
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

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = getActivity().getSharedPreferences(mypreference, MODE_PRIVATE);
        totalAmount1 = prefs.getString("total_price", "0");//"No name defined" is the default value.
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            SharedPreferences prefs = getActivity().getSharedPreferences(mypreference, MODE_PRIVATE);
            totalAmount1 = prefs.getString("total_price", "0");//"No name defined" is the default value.        }
        }
    }
}
