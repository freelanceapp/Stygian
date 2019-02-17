package com.infobite.stygian.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.infobite.stygian.R;

public class ThankyouActivity extends AppCompatActivity {

    Context ctx;
    Button continue_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        ctx = this;
        continue_bt = findViewById(R.id.bt_thanku_continue);
        continue_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://stygianstore.com/tracking-2/"));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        startActivity(new Intent(ctx, MainActivity.class));
    }
}
