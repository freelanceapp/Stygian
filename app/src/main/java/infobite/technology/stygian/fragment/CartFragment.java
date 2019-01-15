package infobite.technology.stygian.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import infobite.technology.stygian.R;
import infobite.technology.stygian.activity.CheckOutActivity;
import infobite.technology.stygian.activity.LoginActivity;
import infobite.technology.stygian.activity.MainActivity;
import infobite.technology.stygian.adapter.AdapterCart;
import infobite.technology.stygian.constant.Constant;
import infobite.technology.stygian.database.HelperManager;
import infobite.technology.stygian.model.ProductDetail;
import infobite.technology.stygian.util.AppPreference;
import infobite.technology.stygian.util.ConnectionDetector;
import infobite.technology.stygian.util.ConstantData;
import infobite.technology.stygian.util.SessionManager;

import static infobite.technology.stygian.activity.MainActivity.cart_number;

@SuppressLint("ValidFragment")
public class CartFragment extends Fragment implements View.OnClickListener {

    Context ctx;
    RecyclerView recyclerView;
    Button place_bt;
    ArrayList<ProductDetail> list;

    HelperManager helperManager;
    ConnectionDetector connectionDetector;
    SessionManager sessionManager;
    Activity activity;

    public CartFragment(Context ctx, Activity activity) {
        this.ctx = ctx;
        this.activity = activity;
        helperManager = new HelperManager(ctx);
        sessionManager = new SessionManager(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        MainActivity.tooltext_tv.setText(ConstantData.CART);
        recyclerView = view.findViewById(R.id.rv_wishlist_recyclerview);
        place_bt = view.findViewById(R.id.bt_wishlist_placeorder);

        list = helperManager.readAllCart();
        AdapterCart adapterCart = new AdapterCart(list, ctx, CartFragment.this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCart);

        place_bt.setVisibility(View.VISIBLE);
        place_bt.setOnClickListener(this);

        setTotal();
        return view;
    }

    public void setTotal() {
        float total = 0;
        ArrayList<ProductDetail> total_list = helperManager.readAllCart();
        cart_number.setText("" + total_list.size());
        AppPreference.setIntegerPreference(ctx, Constant.CART_ITEM_COUNT, total_list.size());
        for (int i = 0; i < total_list.size(); i++) {
            float pr = Float.parseFloat(total_list.get(i).getPrice());
            int qty = total_list.get(i).getQuantity();

            float tot = pr * qty;
            total += tot;
            total = Math.round(total);
        }
        place_bt.setText("Place this Order :   ₹" + total);
    }

    public String getTotal() {
        float total = 0;
        float round_total = 0;
        ArrayList<ProductDetail> total_list = helperManager.readAllCart();
        cart_number.setText(total_list.size());
        AppPreference.setIntegerPreference(ctx, Constant.CART_ITEM_COUNT, total_list.size());
        for (int i = 0; i < total_list.size(); i++) {
            float pr = Float.parseFloat(total_list.get(i).getPrice());
            int qty = total_list.get(i).getQuantity();

            float tot = pr * qty;
            total += tot;
            round_total = Math.round(total);
        }
        return String.valueOf(round_total);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_wishlist_placeorder:
                if (AppPreference.getBooleanPreference(ctx, ConstantData.IS_LOGIN_SKIP)) {
                    startActivity(new Intent(ctx, LoginActivity.class));
                    getActivity().finish();
                } else {
                    ArrayList<ProductDetail> cartlist = helperManager.readAllCart();
                    if (cartlist.size() > 0) {
                        startActivity(new Intent(ctx, CheckOutActivity.class));
                    }
                }
                break;
        }
    }
}
