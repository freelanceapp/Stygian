package com.infobite.stygian.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.infobite.stygian.R;
import com.infobite.stygian.adapter.OrderDetailsAdapter;
import com.infobite.stygian.model.OrderDetail;

public class OrderDetailsActivity extends AppCompatActivity {

    Context ctx;
    ImageView back_iv;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        initXml();
        setData();
    }

    private void setData() {

        ArrayList<OrderDetail> list = new ArrayList<>();
        String data = getIntent().getStringExtra("data");
        try {
            JSONArray datarray = new JSONArray(data);
            if (datarray.length() > 0) {
                for (int i = 0; i < datarray.length(); i++) {
                    JSONObject object = datarray.getJSONObject(i);
                    String name = object.getString("name");
                    String price = object.getString("price");
                    String total = object.getString("total");
                    String qty = object.getString("quantity");
                    list.add(new OrderDetail(name, qty, price, total));
                }
                setAdapter(list);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(ArrayList<OrderDetail> list) {

        OrderDetailsAdapter adapter = new OrderDetailsAdapter(ctx, list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initXml() {
        ctx = this;
        back_iv = findViewById(R.id.iv_orderdet_back);
        recyclerView = findViewById(R.id.rv_orderdet_recycler);

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
