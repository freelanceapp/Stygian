package infobite.technology.stygian.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import infobite.technology.stygian.R;
import infobite.technology.stygian.util.AppPreference;
import infobite.technology.stygian.util.ConstantData;
import infobite.technology.stygian.fragment.Login_Fragment;
import infobite.technology.stygian.util.SessionManager;


public class LoginActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;
    Context ctx;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ctx = this;
        checkSession();
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new Login_Fragment(ctx), ConstantData.Login_Fragment).commit();
        }

        // On close icon click finish activity
        findViewById(R.id.close_activity).setOnClickListener(
                arg0 -> {
                    AppPreference.setBooleanPreference(getApplicationContext(), ConstantData.IS_LOGIN_SKIP, true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });

    }

    private void checkSession() {
        sessionManager = new SessionManager(ctx);
        boolean login = sessionManager.isLoggedIn();
        if (login) {
            startActivity(new Intent(ctx, MainActivity.class));
            finish();
        }
    }

    // Replace Login Fragment with animation
    public void replaceLoginFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new Login_Fragment(ctx),
                        ConstantData.Login_Fragment).commit();
    }

    @Override
    public void onBackPressed() {

        // Find the tag of signup and forgot password fragment
        Fragment SignUp_Fragment = fragmentManager
                .findFragmentByTag(ConstantData.SignUp_Fragment);
        Fragment ForgotPassword_Fragment = fragmentManager
                .findFragmentByTag(ConstantData.ForgotPassword_Fragment);


        if (SignUp_Fragment != null)
            replaceLoginFragment();
        else if (ForgotPassword_Fragment != null)
            replaceLoginFragment();
        else
            super.onBackPressed();
    }
}


