package infobite.technology.stygian.PayPal;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import infobite.technology.stygian.R;
import infobite.technology.stygian.activity.ThankyouActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationActivity extends AppCompatActivity {
    Button confirm_btn;
    private String android_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        //Getting Intent
        Intent intent = getIntent();
        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("Device ID","..."+android_id);
        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));
            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        confirm_btn = (Button)findViewById(R.id.confirm_btn);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Api();

                Intent i = new Intent(ConfirmationActivity.this,ThankyouActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
        TextView textViewId = (TextView) findViewById(R.id.paymentId);
        TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);

        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount+" USD");
    }

   /* public void Api(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PaymentApi>> call = apiService.paymentApi(profile_id);
        call.enqueue(new Callback<List<PaymentApi>>() {
            @Override
            public void onResponse(Call<List<PaymentApi>> call, Response<List<PaymentApi>> response) {

                List<PaymentApi> heroList = response.body();
                // MyProfileResponce signUpResponse = response.body();
                for (int i=0;i<heroList.size();i++) {
                    Log.e("Status",heroList.get(i).getStatus());
                    Log.e("Status",heroList.get(i).getMessage());
                    Intent intent = new Intent(ConfirmationActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<PaymentApi>> call, Throwable t) {
                // Log error here since request failed
                Log.e("Kids...", t.toString());
            }
        });
    }*/
}
