package com.infobite.stygian.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import com.infobite.stygian.R;
import com.infobite.stygian.util.ConnectionDetector;
import com.infobite.stygian.util.SessionManager;
import com.infobite.stygian.util.Utility;
import com.infobite.stygian.util.WebApi;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Context ctx;
    ImageView back_iv;
    EditText fname_et, lname_et, email_et, address_et, city_et, state_et, country_et,
            postcode_et;
    Button submit_bt;

    ConnectionDetector connectionDetector;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initXml();
        boolean internet = connectionDetector.isConnected();
        if (internet) {
            String id = sessionManager.getData(SessionManager.KEY_ID);
            getProfile(id);
        }
    }

    private void initXml() {
        ctx = this;
        fname_et = findViewById(R.id.et_editprof_fname);
        lname_et = findViewById(R.id.et_editprof_lname);
        email_et = findViewById(R.id.et_editprof_email);
        address_et = findViewById(R.id.et_editprof_address);
        city_et = findViewById(R.id.et_editprof_city);
        state_et = findViewById(R.id.et_editprof_state);
        country_et = findViewById(R.id.et_editprof_country);
        postcode_et = findViewById(R.id.et_editprof_postcode);
        submit_bt = findViewById(R.id.bt_editprof_submit);
        back_iv = findViewById(R.id.iv_editpro_back);

        back_iv.setOnClickListener(this);
        submit_bt.setOnClickListener(this);

        sessionManager = new SessionManager(ctx);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.bt_editprof_submit:
                boolean internet = connectionDetector.isConnected();
                if (internet) {
                    submitData();
                } else {
                    Utility.toastView(ctx, ctx.getString(R.string.no_internet));
                }
                break;

            case R.id.iv_editpro_back:
                finish();
                break;
        }
    }

    private void getProfile(String id) {
        Utility.showLoader(ctx);
        AndroidNetworking.get(WebApi.API_USER_PROFILE + id)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utility.hideLoader();
                        try {
                            String fname = response.getString("first_name");
                            String lname = response.getString("last_name");
                            String username = response.getString("username");
                            String email = response.getString("email");
                            setData(fname, lname, username, email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

    private void setData(String fname, String lname, String username, String email) {

        fname_et.setText(fname);
        lname_et.setText(lname);
        email_et.setText(email);
    }


    private void submitData() {

        String fname = fname_et.getText().toString();
        String lname = lname_et.getText().toString();
        String email = email_et.getText().toString();
        String address = address_et.getText().toString();
        String city = city_et.getText().toString();
        String state = state_et.getText().toString();
        String country = country_et.getText().toString();
        String postcode = postcode_et.getText().toString();

        postData(fname, lname, email);
    }

    private void postData(String fname, String lname, String email) {

        String id = sessionManager.getData(SessionManager.KEY_ID);
        Utility.showLoader(ctx);
        AndroidNetworking.post(WebApi.API_EDIT_PROFILE + id)
                .addBodyParameter("first_name", fname)
                .addBodyParameter("last_name", lname)
                .addBodyParameter("email", email)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, "Successfullt Submitted");
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }
}
