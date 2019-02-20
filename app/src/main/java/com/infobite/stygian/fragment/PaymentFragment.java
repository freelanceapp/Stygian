package com.infobite.stygian.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.infobite.stygian.R;
import com.infobite.stygian.adapter.AdapterOffer;
import com.infobite.stygian.constant.Constant;
import com.infobite.stygian.database.DatabaseHandler;
import com.infobite.stygian.model.CouponModel;
import com.infobite.stygian.util.ConnectionDetector;
import com.infobite.stygian.util.ConstantData;
import com.infobite.stygian.util.SessionManager;
import com.infobite.stygian.util.Utility;
import com.infobite.stygian.util.WebApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.infobite.stygian.activity.SplashActivity.mypreference;

@SuppressLint("ValidFragment")
public class PaymentFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    Context ctx;
    LinearLayout next_ll;
    TextView total_tv, tv_payment_offer;
    RadioButton cob_rb;
    Activity activity;
    SessionManager sessionManager;
    Button couponCodeBtn;
    ConnectionDetector connectionDetector;
    static int page = 0;
    String api = "";
    EditText EtcouponCode;
    private String offerCode = "";
    LinearLayout offer_layout;
    String currentDateTimeString;
    public DatabaseHandler databaseCart;
    private String DATABASE_CART = "cart.db";
    public static String totlaPrice = "0";
    RecyclerView rv_offer_list;
    private ArrayList<CouponModel> walletModelArrayList = new ArrayList<>();
    private AdapterOffer adapterOffer;
    Calendar c;

    @SuppressLint("ValidFragment")
    public PaymentFragment(Context ctx) {
        this.ctx = ctx;
        this.activity = activity;
        sessionManager = new SessionManager(ctx);
        databaseCart = new DatabaseHandler(ctx, DATABASE_CART);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, null);
        initXml(view);
        return view;
    }

    private void initXml(View view) {
        c = Calendar.getInstance();
        boolean internet = connectionDetector.isConnected();
        if (internet) {
            api = WebApi.API_OFFERS;
            getdata1();
        } else {
            Utility.toastView(ctx, ctx.getString(R.string.no_internet));
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        currentDateTimeString = df.format(c.getTime());
        next_ll = view.findViewById(R.id.ll_payment_next);
        total_tv = view.findViewById(R.id.tv_payment_total);
        rv_offer_list = view.findViewById(R.id.rv_offer_list);
        tv_payment_offer = view.findViewById(R.id.tv_payment_offer);
        offer_layout = view.findViewById(R.id.offer_layout);
        cob_rb = view.findViewById(R.id.rb_payment_cod);
        couponCodeBtn = view.findViewById(R.id.couponCodeBtn);
        EtcouponCode = view.findViewById(R.id.EtcouponCode);
        next_ll.setOnClickListener(this);
        cob_rb.setOnClickListener(this);
        couponCodeBtn.setOnClickListener(this);
        total_tv.setText(Utility.getCartTotal(databaseCart));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_payment_next:
                sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "PayPal");
                SharedPreferences.Editor editor1 = getActivity().getSharedPreferences(mypreference, MODE_PRIVATE).edit();
                editor1.putString("total_price", total_tv.getText().toString());
                editor1.putString("offer_price", tv_payment_offer.getText().toString());
                editor1.apply();
                // ((CheckOutActivity) getActivity()).setPosition(2);
                ConfirmationFragment fragment = new ConfirmationFragment(ctx);
                Utility.setFragment1(fragment, ctx, ConstantData.HOME);

                break;
            case R.id.rb_payment_cod:
                sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "PayPal");
                //((CheckOutActivity) getActivity()).setPosition(2);

                break;
           /* case R.id.rb_payment_credit:
                sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "online");
                //((CheckOutActivity) getActivity()).setPosition(2);
                break;*/
            case R.id.couponCodeBtn:
                offerCode = EtcouponCode.getText().toString();
                boolean internet = connectionDetector.isConnected();
                if (internet) {
                    if (!offerCode.equals("")) {
                        api = WebApi.API_OFFERS;
                        getdata();
                    } else {
                        EtcouponCode.setError("Enter Code");
                    }
                } else {
                    Utility.toastView(ctx, ctx.getString(R.string.no_internet));
                }
                break;
        }
    }

    public void getdata() {
        Utility.showLoader(ctx);
        AndroidNetworking.get(api)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Utility.hideLoader();
                        setResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

    private void setResponse(JSONArray response) {
        if (response.length() > 0) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String id = object.getString("id");
                    String code = object.getString("code");
                    String amount = object.getString("amount");
                    String date_created = object.getString("date_created");
                    String date_created_gmt = object.getString("date_created_gmt");
                    String date_modified = object.getString("date_modified");
                    String date_modified_gmt = object.getString("date_modified_gmt");
                    String discount_type = object.getString("discount_type");
                    String description = object.getString("description");
                    String date_expires = object.getString("date_expires");
                    String date_expires_gmt = object.getString("date_expires_gmt");
                    String usage_count = object.getString("usage_count");
                    String individual_use = object.getString("individual_use");
                    String product_ids = object.getString("product_ids");
                    String excluded_product_ids = object.getString("excluded_product_ids");
                    String minimum_amount = object.getString("minimum_amount");
                    String maximum_amount = object.getString("maximum_amount");

                    if (code.equalsIgnoreCase(offerCode)) {
                        String strSplit[] = date_expires.split("T");

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        Date strDate = sdf.parse(strSplit[0]);

                        long currentTime = System.currentTimeMillis();
                        long expireTime = strDate.getTime();

                        if (expireTime > currentTime) {

                            float amount1 = Float.parseFloat(total_tv.getText().toString());
                            float offer1 = Float.parseFloat(amount);

                            if (offer1 < amount1) {
                                Toast.makeText(ctx, "ID " + id + " amount " + amount, Toast.LENGTH_SHORT).show();
                                float total1 = amount1 - offer1;
                                tv_payment_offer.setText("" + offer1);
                                total_tv.setText("" + total1);
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences(Constant.TOTAL_AMOUNT, MODE_PRIVATE).edit();
                                sessionManager.setData(SessionManager.KEY_ORDER_PRICE, String.valueOf(total1));

                                offer_layout.setVisibility(View.GONE);
                                editor.putString("Total", total_tv.getText().toString());
                                editor.putString("Offer", tv_payment_offer.getText().toString());
                                editor.apply();
                            } else {
                                Toast.makeText(ctx, "The offer is not valid for this product.", Toast.LENGTH_SHORT).show();
                                tv_payment_offer.setText("0");
                                total_tv.setText("" + amount1);
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences(Constant.TOTAL_AMOUNT, MODE_PRIVATE).edit();
                                sessionManager.setData(SessionManager.KEY_ORDER_PRICE, String.valueOf(amount1));

                                offer_layout.setVisibility(View.GONE);
                                editor.putString("Total", total_tv.getText().toString());
                                editor.putString("Offer", tv_payment_offer.getText().toString());
                                editor.apply();
                            }

                        } else {
                            Toast.makeText(ctx, "Expired Coupon ", Toast.LENGTH_SHORT).show();
                        }
                    }/*else  {
                        Toast.makeText(ctx,"Invalid Coupon", Toast.LENGTH_SHORT).show();
                    }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getdata1() {
        //Utility.showLoader(ctx);
        AndroidNetworking.get(api)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Utility.hideLoader();
                        setResponse1(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        //Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

    private void setResponse1(JSONArray response) {

        if (response.length() > 0) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String id = object.getString("id");
                    String code = object.getString("code");
                    String amount = object.getString("amount");
                    String date_created = object.getString("date_created");
                    String date_created_gmt = object.getString("date_created_gmt");
                    String date_modified = object.getString("date_modified");
                    String date_modified_gmt = object.getString("date_modified_gmt");
                    String discount_type = object.getString("discount_type");
                    String description = object.getString("description");
                    String date_expires = object.getString("date_expires");
                    String date_expires_gmt = object.getString("date_expires_gmt");
                    String usage_count = object.getString("usage_count");
                    String individual_use = object.getString("individual_use");
                    String product_ids = object.getString("product_ids");
                    String excluded_product_ids = object.getString("excluded_product_ids");
                    String minimum_amount = object.getString("minimum_amount");
                    String maximum_amount = object.getString("maximum_amount");

                    walletModelArrayList.add(new CouponModel(id, code, date_expires, description, amount, minimum_amount, maximum_amount));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (walletModelArrayList.size() > 0) {
                setAdapter(walletModelArrayList);
            }
        }
    }

    private void setAdapter(ArrayList<CouponModel> walletModelArrayList) {
        adapterOffer = new AdapterOffer(walletModelArrayList, ctx);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        rv_offer_list.setLayoutManager(layoutManager);
        rv_offer_list.setItemAnimator(new DefaultItemAnimator());
        rv_offer_list.setAdapter(adapterOffer);
    }
}
