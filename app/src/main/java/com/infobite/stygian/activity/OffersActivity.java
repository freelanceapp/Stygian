package com.infobite.stygian.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.infobite.stygian.R;
import com.infobite.stygian.adapter.AdapterOffer;
import com.infobite.stygian.model.CouponModel;
import com.infobite.stygian.retrofit_provider.RetrofitService;
import com.infobite.stygian.util.BaseActivity;
import com.infobite.stygian.util.ConnectionDetector;
import com.infobite.stygian.util.ConnectionDetector1;
import com.infobite.stygian.util.Utility;
import com.infobite.stygian.util.WebApi;

public class OffersActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back_Btn1;
    private RecyclerView recyclerView;
    private AdapterOffer adapterOffer;
    private ArrayList<CouponModel> walletModelArrayList = new ArrayList<>();
    TextView walletAmount;
    ConnectionDetector connectionDetector;
    static int page = 0;
    String api = "";
    Context ctx;
    String type;
    String user_id = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
       /* SharedPreferences prefs = getSharedPreferences(mypreference, MODE_PRIVATE);
        user_id = prefs.getString("user_id", "0"); //0 is the default value.
        Log.e("USER ID", ".."+user_id);*/
        init();

    }

    private void init()
    {
        mContext = this;
        cd = new ConnectionDetector1(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();
        this.ctx = ctx;
        this.type = type;
        boolean internet = connectionDetector.isConnected();
        if (internet) {
            api = WebApi.API_OFFERS;
            getdata();
        } else {
            Utility.toastView(ctx, ctx.getString(R.string.no_internet));
        }

        back_Btn1 = findViewById(R.id.back_Btn1);
        back_Btn1.setOnClickListener(this);
        recyclerView = findViewById(R.id.offerList);
        adapterOffer = new AdapterOffer(walletModelArrayList, mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(OffersActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterOffer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_Btn1 :
                finish();
                break;
        }
    }


    public void getdata() {
        //Utility.showLoader(ctx);
        AndroidNetworking.get(api)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Utility.hideLoader();
                        setResponse(response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        //Utility.hideLoader();
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

                    walletModelArrayList.add(new CouponModel(id,code,date_expires,description,amount,minimum_amount,maximum_amount));
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(OffersActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterOffer);
    }
}
