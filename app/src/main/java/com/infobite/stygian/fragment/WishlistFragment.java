package com.infobite.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infobite.stygian.adapter.AdapterWishlist;
import com.infobite.stygian.R;

import java.util.ArrayList;

import com.infobite.stygian.activity.MainActivity;
import com.infobite.stygian.database.HelperManager;
import com.infobite.stygian.model.ProductDetail;
import com.infobite.stygian.util.ConstantData;
import com.infobite.stygian.util.Utility;

@SuppressLint("ValidFragment")
public class WishlistFragment extends Fragment implements View.OnClickListener {

    Context ctx;
    RecyclerView recyclerView;
    ArrayList<ProductDetail> list;

    HelperManager helperManager;

    public WishlistFragment(Context ctx) {
        this.ctx = ctx;
        helperManager = new HelperManager(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        MainActivity.tooltext_tv.setText("Wishlist");
        recyclerView = view.findViewById(R.id.rv_wishlist_recyclerview);

        list = helperManager.readAllWishlist();
        AdapterWishlist addtoCardAdapter = new AdapterWishlist(list, ctx);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(addtoCardAdapter);
        view.findViewById(R.id.btnBack).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                onBackClick();
                break;
        }
    }

    private void onBackClick() {
        HomeFragment homeFragment = new HomeFragment();
        Utility.setFragment(homeFragment, ctx, ConstantData.HOME);
    }
}
