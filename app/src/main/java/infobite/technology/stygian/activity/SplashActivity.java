package infobite.technology.stygian.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import infobite.technology.stygian.R;
import infobite.technology.stygian.util.SessionManager;


public class SplashActivity extends AppCompatActivity {

    Context ctx;
    SessionManager sessionManager;
    public static String mypreference = "Stygian";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ctx = this;
        sessionManager = new SessionManager(ctx);
        sessionManager.setData(SessionManager.KEY_ORDER_NAME, "");
        sessionManager.setData(SessionManager.KEY_ORDER_MOBILE, "");
        sessionManager.setData(SessionManager.KEY_ORDER_ADDRESS, "");
        sessionManager.setData(SessionManager.KEY_ORDER_CITY, "");
        sessionManager.setData(SessionManager.KEY_ORDER_STATE, "");
        sessionManager.setData(SessionManager.KEY_ORDER_COUNTRY, "");
        sessionManager.setData(SessionManager.KEY_ORDER_ZIPCODE, "");
        sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ctx, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}
