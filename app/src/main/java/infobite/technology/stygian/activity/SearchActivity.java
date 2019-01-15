package infobite.technology.stygian.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import infobite.technology.stygian.R;
import infobite.technology.stygian.adapter.AllProductListAdapter;
import infobite.technology.stygian.model.all_product_modal.ProductList;
import infobite.technology.stygian.retrofit_provider.RetrofitApiClient;
import infobite.technology.stygian.retrofit_provider.RetrofitService;
import infobite.technology.stygian.retrofit_provider.WebResponse;
import infobite.technology.stygian.util.Alerts;
import infobite.technology.stygian.util.NetworkDetector;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private AllProductListAdapter customerListAdapter;
    private List<ProductList> customerUserList = new ArrayList<>();
    private NetworkDetector networkDetector;
    private RetrofitApiClient retrofitApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;
        networkDetector = new NetworkDetector(mContext);
        init();
    }

    private void init() {
        retrofitApiClient = RetrofitService.getRetrofit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((ImageView) findViewById(R.id.backBtn)).setOnClickListener(this);

        customerListAdapter = new AllProductListAdapter(mContext, R.layout.row_product_item, customerUserList);
        ListView listViewSearchItem = (ListView) findViewById(R.id.listViewSearchItem);
        listViewSearchItem.setAdapter(customerListAdapter);

        searchInit();
    }

   /* private void allProductApi() {
        if (networkDetector.isNetworkAvailable()) {
            RetrofitService.getResponseBody(new Dialog(mContext), retrofitApiClient.allProductList(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    customerListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            networkDetector.show(mContext);
        }
    }
*/
    private void searchInit() {
        ((EditText) findViewById(R.id.edtSearch))
                .addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence cs, int start, int before, int count) {
                        customerListAdapter.getFilter().filter(cs);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                break;
        }
    }
}
