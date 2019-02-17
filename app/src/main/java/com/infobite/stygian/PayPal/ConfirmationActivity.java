package com.infobite.stygian.PayPal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.infobite.stygian.R;
import com.infobite.stygian.activity.ThankyouActivity;
import com.infobite.stygian.database.DatabaseHandler;
import com.infobite.stygian.model.ProductDetail;
import com.infobite.stygian.util.SessionManager;
import com.infobite.stygian.util.Utility;
import com.infobite.stygian.util.WebApi;

import static com.infobite.stygian.activity.SplashActivity.mypreference;
import static rx.plugins.RxJavaHooks.clear;

public class ConfirmationActivity extends AppCompatActivity {
    Button confirm_btn;
    private String android_id;
    SessionManager sessionManager;
    Context ctx;
    String totalAmount1;
    public DatabaseHandler databaseCart;
    private String DATABASE_CART = "cart.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        //Getting Intent
        ctx = getApplicationContext();
        Intent intent = getIntent();
        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("Device ID","..."+android_id);
        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));
            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        SharedPreferences prefs = getSharedPreferences(mypreference, MODE_PRIVATE);
        totalAmount1 = prefs.getString("total_price", "0");//"No name defined" is the default value.
        databaseCart = new DatabaseHandler(ctx, DATABASE_CART);


        getData();
        sessionManager = new SessionManager(ctx);
        //databaseCart = new DatabaseHandler(ctx, DATABASE_CART);
        confirm_btn = (Button)findViewById(R.id.confirm_btn);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Api();

                Intent i = new Intent(ConfirmationActivity.this,ThankyouActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
        TextView textViewId = (TextView) findViewById(R.id.paymentId);
        TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);

        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount+" USD");
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
                        startActivity(new Intent(ctx, ThankyouActivity.class));


                    }

                    @Override
                    public void onError(ANError anError) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

}
