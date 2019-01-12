package infobite.technology.stygian.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import infobite.technology.stygian.R;

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
                finishAffinity();
                startActivity(new Intent(ctx, MainActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        startActivity(new Intent(ctx, MainActivity.class));
    }
}
