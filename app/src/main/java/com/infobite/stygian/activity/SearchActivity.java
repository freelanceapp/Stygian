package com.infobite.stygian.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.infobite.stygian.R;
import com.infobite.stygian.adapter.AllProductListAdapter;
import com.infobite.stygian.model.ProductDetail;
import com.infobite.stygian.model.all_product_modal.AllProductMainModal;
import com.infobite.stygian.retrofit_provider.RetrofitApiClient;
import com.infobite.stygian.retrofit_provider.RetrofitService;
import com.infobite.stygian.retrofit_provider.WebResponse;
import com.infobite.stygian.util.Alerts;
import com.infobite.stygian.util.NetworkDetector;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private AllProductListAdapter customerListAdapter;
    private List<AllProductMainModal> customerUserList = new ArrayList<>();
    private NetworkDetector networkDetector;
    private RetrofitApiClient retrofitApiClient;
    private ListView listViewSearchItem;

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

        listViewSearchItem = (ListView) findViewById(R.id.listViewSearchItem);

        allProductApi();
        searchInit();
        sendProductDetail();
    }

    private void allProductApi() {
        if (networkDetector.isNetworkAvailable()) {
            RetrofitService.getAllProduct(new Dialog(mContext), retrofitApiClient.allProductList(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    customerUserList.clear();
                    customerUserList.addAll((Collection<? extends AllProductMainModal>) result.body());

                    customerListAdapter = new AllProductListAdapter(mContext, R.layout.row_product_item, customerUserList);
                    listViewSearchItem.setAdapter(customerListAdapter);
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

    private void sendProductDetail() {
        listViewSearchItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AllProductMainModal allProductMainModal = customerUserList.get(i);

                Gson gson = new GsonBuilder().setLenient().create();
                String attributeArray = gson.toJson(allProductMainModal.getAttributes());

                String strImageArray = "";
                String strImage = "";
                if (allProductMainModal.getImages().size() > 0) {
                    strImageArray = gson.toJson(allProductMainModal.getImages());
                    strImage = allProductMainModal.getImages().get(0).getSrc();
                }

                ProductDetail productDetail = new ProductDetail(String.valueOf(allProductMainModal.getId()), allProductMainModal.getName(),
                        allProductMainModal.getDescription(), allProductMainModal.getPrice(), allProductMainModal.getRegularPrice(),
                        allProductMainModal.getSalePrice(), allProductMainModal.getPriceHtml(), strImage, strImageArray,
                        attributeArray, 1);

                Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                intent.putExtra("data", productDetail);
                intent.putExtra("link", allProductMainModal.getPermalink());
                startActivity(intent);
            }
        });
    }
}
