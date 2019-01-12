package infobite.technology.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import infobite.technology.stygian.adapter.AdapterWishlist;
import infobite.technology.stygian.R;

import java.util.ArrayList;

import infobite.technology.stygian.activity.MainActivity;
import infobite.technology.stygian.database.HelperManager;
import infobite.technology.stygian.model.ProductDetail;

@SuppressLint("ValidFragment")
public class WishlistFragment extends Fragment {

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

        return view;
    }
}
