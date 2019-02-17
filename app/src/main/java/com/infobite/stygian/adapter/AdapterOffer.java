package com.infobite.stygian.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.infobite.stygian.R;
import com.infobite.stygian.database.HelperManager;
import com.infobite.stygian.model.CouponModel;


public class AdapterOffer extends RecyclerView.Adapter<AdapterOffer.MyViewHolder> {

    ArrayList<CouponModel> list;
    Context context;
    HelperManager helperManager;
    Fragment fragment;

    public AdapterOffer(ArrayList<CouponModel> list, Context context) {
        this.list = list;
        this.context = context;
        helperManager = new HelperManager(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView offer_detail, offer_date, offer_code;
        ImageView offer_img;

        public MyViewHolder(View view) {
            super(view);
            offer_detail = view.findViewById(R.id.offer_detail);
            offer_date = view.findViewById(R.id.offer_date);

            offer_img = view.findViewById(R.id.offer_img);
            offer_code = view.findViewById(R.id.offer_code);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_offers, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CouponModel productDetail = list.get(position);
        holder.offer_detail.setText(productDetail.getCoupon_detail());
        holder.offer_date.setText(productDetail.getCoupon_exp_date());
        holder.offer_code.setText(productDetail.getCoupon_code());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
