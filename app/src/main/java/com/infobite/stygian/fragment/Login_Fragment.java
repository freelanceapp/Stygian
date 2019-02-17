package com.infobite.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.infobite.stygian.R;
import com.infobite.stygian.activity.MainActivity;
import com.infobite.stygian.constant.Constant;
import com.infobite.stygian.util.AppPreference;
import com.infobite.stygian.util.ConnectionDetector;
import com.infobite.stygian.util.ConstantData;
import com.infobite.stygian.util.CustomToast;
import com.infobite.stygian.util.SessionManager;
import com.infobite.stygian.util.Utility;
import com.infobite.stygian.util.WebApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;
import static com.infobite.stygian.activity.SplashActivity.mypreference;

@SuppressLint("ValidFragment")
public class Login_Fragment extends Fragment implements OnClickListener {

    private static View view;
    private static EditText emailid_et, password_et;
    private static Button loginButton;
    private static TextView forgotPassword;
    private static LinearLayout signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    ProgressBar loginProgress;
    String getEmailId, getPassword;

    Context ctx;
    ConnectionDetector connectionDetector;
    SessionManager sessionManager;

    @SuppressLint("ValidFragment")
    public Login_Fragment(Context ctx) {
        this.ctx = ctx;
        sessionManager = new SessionManager(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        loginProgress = (ProgressBar) view.findViewById(R.id.loginProgress);
        emailid_et = (EditText) view.findViewById(R.id.login_emailid);
        password_et = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (LinearLayout) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            //signUp.setBackgroundResource(csl);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                signUp.setBackgroundTintList(csl);
            }

        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password_et
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {

                        // If it is checkec then show password_et else hide
                        // password_et
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password_et.setInputType(InputType.TYPE_CLASS_TEXT);
                            password_et.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password_et
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password_et.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password_et.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password_et

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;

            case R.id.forgot_password:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ForgotPassword_Fragment(),
                                ConstantData.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(ctx),
                                ConstantData.SignUp_Fragment).commit();
                break;
        }

    }

    private void checkValidation() {

        getEmailId = emailid_et.getText().toString();
        getPassword = password_et.getText().toString();

        Pattern p = Pattern.compile(ConstantData.regEx);
        Matcher m = p.matcher(getEmailId);

        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");

        } else if (!m.find()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
        } else {
            boolean internet = connectionDetector.isConnected();
            if (internet) {
                loginUser();
            } else {
                new CustomToast().Show_Toast(getActivity(), view, ctx.getString(R.string.no_internet));
            }
        }
    }

    private void loginUser() {

        Utility.showLoader(ctx);
        AndroidNetworking.post(WebApi.API_LOGIN)
                .addBodyParameter("username", getEmailId)
                .addBodyParameter("password", getPassword)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utility.hideLoader();
                        setResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utility.hideLoader();
                    }
                });
    }

    private void setResponse(JSONObject response) {

        try {
            String status = response.getString("status");
            if (status.equals("ok")) {

                JSONObject user_obj = response.getJSONObject("user");
                String user_id = user_obj.getString("id");
                String username = user_obj.getString("username");
                String user_email = user_obj.getString("email");
                String diaplay_name = user_obj.getString("displayname");
                String fname = user_obj.getString("firstname");
                String lname = user_obj.getString("lastname");

                AppPreference.setStringPreference(ctx, Constant.USERNAME, fname + " " + lname);
                AppPreference.setBooleanPreference(ctx, Constant.IS_LOGIN, true);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences(mypreference, MODE_PRIVATE).edit();
                editor.putString("user_id", user_id);
                editor.apply();

                sessionManager.createLoginSession(user_id, username, diaplay_name, user_email);
                startActivity(new Intent(ctx, MainActivity.class));
                getActivity().finish();

            } else if (status.equals("error")) {
                String error = response.getString("error");
                new CustomToast().Show_Toast(ctx, view, error);
            } else {
                new CustomToast().Show_Toast(ctx, view, ctx.getString(R.string.something));
            }
            clear();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        emailid_et.setText("");
        password_et.setText("");
    }


}
