package com.infobite.stygian.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.infobite.stygian.R;
import com.infobite.stygian.adapter.RecyclerviewAdapterSubCategory;
import com.infobite.stygian.model.category_modal.ProductCategoryList;
import com.infobite.stygian.retrofit_provider.RetrofitApiClient;
import com.infobite.stygian.retrofit_provider.RetrofitService;
import com.infobite.stygian.retrofit_provider.WebResponse;
import com.infobite.stygian.util.Alerts;
import com.infobite.stygian.util.ConnectionDetector;
import com.infobite.stygian.util.ConstantData;
import com.infobite.stygian.util.Utility;

import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context ctx;
    private RecyclerView recyclerViewCategory;
    private ImageView back_iv;
    private TextView title_tv;

    private List<ProductCategoryList> list = new ArrayList<>();

    private ConnectionDetector connectionDetector;
    private RetrofitApiClient retrofitApiClient;
    private RecyclerviewAdapterSubCategory adapterSubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
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
        RetrofitService.getCategoryList(new Dialog(ctx), retrofitApiClient.categoryList(id), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                list.clear();
                assert result.body() != null;
                list.addAll((Collection<? extends ProductCategoryList>) result.body());
                adapterSubCategory.notifyDataSetChanged();
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(ctx, error);
            }
        });
    }

    private void initXml() {
        ctx = this;
        retrofitApiClient = RetrofitService.getRetrofit();
        recyclerViewCategory = findViewById(R.id.recyclerViewCategory);
        back_iv = findViewById(R.id.iv_subcate_back);
        title_tv = findViewById(R.id.tv_subcate_title);

        title_tv.setText(getIntent().getStringExtra(ConstantData.ACTIVITY_TYPE));
        back_iv.setOnClickListener(this);

        recyclerViewCategory.setHasFixedSize(true);
        adapterSubCategory = new RecyclerviewAdapterSubCategory(list, ctx, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ctx, 2);
        recyclerViewCategory.setLayoutManager(gridLayoutManager);
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCategory.setAdapter(adapterSubCategory);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_subcate_back:
                finish();
                break;
            case R.id.rlItems:
                int pos = Integer.parseInt(view.getTag().toString());

                String strId = String.valueOf(list.get(pos).getId());
                startActivity(new Intent(ctx, ProductsActivity.class)
                        .putExtra(ConstantData.ACTIVITY_ID, strId)
                        .putExtra(ConstantData.ACTIVITY_TYPE, list.get(pos).getName()));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        startActivity(new Intent(ctx, ProductsActivity.class)
                .putExtra(ConstantData.ACTIVITY_ID, list.get(i).getId())
                .putExtra(ConstantData.ACTIVITY_TYPE, list.get(i).getName()));
    }
}
