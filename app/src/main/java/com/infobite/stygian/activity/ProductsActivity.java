package com.infobite.stygian.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.infobite.stygian.R;
import com.infobite.stygian.adapter.ProductAdapter;
import com.infobite.stygian.model.ProductDetail;
import com.infobite.stygian.util.ConnectionDetector;
import com.infobite.stygian.util.ConstantData;
import com.infobite.stygian.util.JsonParser;
import com.infobite.stygian.util.Utility;
import com.infobite.stygian.util.WebApi;

public class ProductsActivity extends AppCompatActivity implements View.OnClickListener {

    Context ctx;
    ImageView back_iv, search_iv, searchcancel_iv;
    TextView title_tv;
    EditText search_et;
    RecyclerView recyclerView;
    Button loadmore_bt;
    int page = 0;

    ArrayList<ProductDetail> list, search_list;
    ArrayList<String> productLink = new ArrayList<>();
    ArrayList<String> productLinkA = new ArrayList<>();
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        initXml();
        boolean internet = connectionDetector.isConnected();
        if (internet) {
            String id = getIntent().getStringExtra(ConstantData.ACTIVITY_ID);
            getData(id);
        } else {
            Utility.toastView(ctx, ctx.getString(R.string.no_internet));
        }
    }

    private void getData(String id) {
        int pos = list.size() - 1;
        page++;
        Utility.showLoader(ctx);
        AndroidNetworking.get(WebApi.API_PRODUCTS + id + "&page=" + page)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Utility.hideLoader();
                        list = JsonParser.getProducts(response);

                        productLink = new ArrayList<>();
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = null;
                                try {
                                    object = response.getJSONObject(i);
                                    String link = object.getString("permalink");
                                    productLink.add(link);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if (list.size() > 0) {
                            setAdapter(list, pos, productLink);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

    private void setAdapter(ArrayList<ProductDetail> list, int pos, ArrayList<String> productLink) {
        ProductAdapter adapter = new ProductAdapter(list, ctx, productLink);
        GridLayoutManager layoutManager = new GridLayoutManager(ctx, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(pos);
    }

    private void initXml() {
        ctx = this;
        recyclerView = findViewById(R.id.rv_products_recyclerview);
        loadmore_bt = findViewById(R.id.bt_products_loadmore);
        back_iv = findViewById(R.id.iv_products_back);
        search_iv = findViewById(R.id.iv_products_search);
        searchcancel_iv = findViewById(R.id.iv_products_searchcancel);
        search_et = findViewById(R.id.et_products_search);
        title_tv = findViewById(R.id.tv_products_title);
        list = new ArrayList<>();

        title_tv.setText(getIntent().getStringExtra(ConstantData.ACTIVITY_TYPE));
        back_iv.setOnClickListener(this);
        search_iv.setOnClickListener(this);
        searchcancel_iv.setOnClickListener(this);
        loadmore_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_products_loadmore:
                String id = getIntent().getStringExtra(ConstantData.ACTIVITY_ID);
                getData(id);
                break;

            case R.id.iv_products_back:
                finish();
                break;

            case R.id.iv_products_search:
                search_list = new ArrayList<>();
                productLinkA = new ArrayList<>();
                String text = search_et.getText().toString();
                if (!text.equals("")) {
                    search_iv.setVisibility(View.GONE);
                    searchcancel_iv.setVisibility(View.VISIBLE);
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getName().contains(text)) {
                                search_list.add(list.get(i));
                                productLinkA.add(productLink.get(i));
                            }
                        }
                        setAdapter(search_list, 0, productLinkA);
                    }
                }
                break;

            case R.id.iv_products_searchcancel:
                search_et.setText("");
                search_iv.setVisibility(View.VISIBLE);
                searchcancel_iv.setVisibility(View.GONE);
                setAdapter(list, 0, productLink);
                break;

        }
    }
}
