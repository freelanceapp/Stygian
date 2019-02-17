package com.infobite.stygian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.infobite.stygian.R;
import com.infobite.stygian.activity.ProductDetailsActivity;
import com.infobite.stygian.model.ProductDetail;

import java.util.ArrayList;


public class AdapterWishlist extends RecyclerView.Adapter<AdapterWishlist.MyViewHolder> {

    ArrayList<ProductDetail> list;
    Context context;

    public AdapterWishlist(ArrayList<ProductDetail> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_tv, size_tv, color_tv, price_tv, qty_tv;
        ImageView pro_image_iv, plus_iv, minus_iv;
        LinearLayout attrib_ll;
        RelativeLayout action_ll;

        public MyViewHolder(View view) {
            super(view);
            name_tv = view.findViewById(R.id.tv_adpcart_name);
            size_tv = view.findViewById(R.id.tv_adpcart_size);
            color_tv = view.findViewById(R.id.tv_adpcart_color);
            price_tv = view.findViewById(R.id.tv_adpcart_price);
            qty_tv = view.findViewById(R.id.tv_adpcart_qty);

            pro_image_iv = view.findViewById(R.id.iv_adpcart_image);
            plus_iv = view.findViewById(R.id.iv_adpcart_plus);
            minus_iv = view.findViewById(R.id.iv_adpcart_minus);
            attrib_ll = view.findViewById(R.id.ll_adpcart_attrib);
            action_ll = view.findViewById(R.id.ll_adpcart_action);

            attrib_ll.setVisibility(View.GONE);
            action_ll.setVisibility(View.GONE);
        }


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adp_cartview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductDetail productDetail = list.get(position);
        holder.name_tv.setText(productDetail.getName());

        float price = Float.parseFloat(productDetail.getPrice());
        float round_pr = Math.round(price);

        holder.price_tv.setText("₹ " + round_pr);
        holder.size_tv.setText("Size : S");
        holder.color_tv.setText("Color : Black");

        Picasso.with(context)
                .load(productDetail.getImage())
                .placeholder(R.drawable.cart_default_img)
                .error(R.drawable.cart_default_img)
                .resize(300, 300)
                .into(holder.pro_image_iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ProductDetailsActivity.class)
                        .putExtra("data", list.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
