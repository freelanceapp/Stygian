package com.infobite.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import com.infobite.stygian.R;
import com.infobite.stygian.activity.EditProfileActivity;
import com.infobite.stygian.util.ConnectionDetector;
import com.infobite.stygian.util.SessionManager;
import com.infobite.stygian.util.Utility;
import com.infobite.stygian.util.WebApi;


@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment implements View.OnClickListener {

    Context ctx;
    View view;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;

    @SuppressLint("ValidFragment")
    public ProfileFragment(Context ctx) {
        this.ctx = ctx;
        sessionManager = new SessionManager(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        boolean internet = connectionDetector.isConnected();
        if (internet) {
            String id = sessionManager.getData(SessionManager.KEY_ID);
            getProfile(id);
        } else {
            Utility.toastView(ctx, ctx.getString(R.string.no_internet));
        }
        return view;
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

        TextView fname_tv = view.findViewById(R.id.tv_profile_fname);
        TextView lname_tv = view.findViewById(R.id.tv_profile_lname);
        TextView username_tv = view.findViewById(R.id.tv_profile_username);
        TextView email_tv = view.findViewById(R.id.tv_profile_email);
        ImageView edit_iv = view.findViewById(R.id.iv_profile_edit);

        fname_tv.setText("First Name : " + fname);
        lname_tv.setText("Last Name : " + lname);
        username_tv.setText("Username : " + username);
        email_tv.setText("E-mail : " + email);

        edit_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        startActivity(new Intent(ctx, EditProfileActivity.class));
    }
}
