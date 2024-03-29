package com.infobite.stygian.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.infobite.stygian.R;
import com.infobite.stygian.adapter.AdapterWellet;
import com.infobite.stygian.model.wallet_responce.WalletModel;
import com.infobite.stygian.retrofit_provider.RetrofitService;
import com.infobite.stygian.util.BaseActivity;
import com.infobite.stygian.util.ConnectionDetector;
import com.infobite.stygian.util.ConnectionDetector1;
import com.infobite.stygian.util.Utility;
import com.infobite.stygian.util.WebApi;

import static com.infobite.stygian.activity.SplashActivity.mypreference;

public class WalletActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back_Btn;
    private RecyclerView recyclerView;
    private AdapterWellet adapterWellet;
    private ArrayList<WalletModel> walletModelArrayList = new ArrayList<>();
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
        setContentView(R.layout.activity_wallet);
        SharedPreferences prefs = getSharedPreferences(mypreference, MODE_PRIVATE);
        user_id = prefs.getString("user_id", "0"); //0 is the default value.
        Log.e("USER ID", ".."+user_id);
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
            if (!user_id.equals("0")) {
                api = WebApi.API_WALLET;
                getdata();
            }else {
                    Toast.makeText(mContext, "login user", Toast.LENGTH_SHORT).show();            }
        } else {
            Utility.toastView(ctx, ctx.getString(R.string.no_internet));
        }

        back_Btn = findViewById(R.id.back_Btn);
        back_Btn.setOnClickListener(this);
        recyclerView = findViewById(R.id.welletList);
        walletAmount = findViewById(R.id.walletAmount);
        adapterWellet = new AdapterWellet(walletModelArrayList, mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(WalletActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterWellet);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_Btn :

                finish();
                break;
        }
    }


    public void getdata() {
        //Utility.showLoader(ctx);
        AndroidNetworking.get(api + user_id)
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
                    walletAmount.setText(""+response.getJSONObject(0).getString("amount"));
                    String transaction_id = object.getString("transaction_id");
                    String blog_id = object.getString("blog_id");
                    String user_id = object.getString("user_id");
                    String type = object.getString("type");
                    String amount = object.getString("amount");
                    String balance = object.getString("balance");
                    String currency = object.getString("currency");
                    String details = object.getString("details");
                    String deleted = object.getString("deleted");
                    String date = object.getString("date");

                    walletModelArrayList.add(new WalletModel(transaction_id,blog_id,user_id,type,amount,balance,currency,details,deleted,date));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (walletModelArrayList.size() > 0) {
                setAdapter(walletModelArrayList);
            }
        }

    }

    private void setAdapter(ArrayList<WalletModel> walletModelArrayList) {
        adapterWellet = new AdapterWellet(walletModelArrayList, ctx);
        LinearLayoutManager layoutManager = new LinearLayoutManager(WalletActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterWellet);
    }
}
