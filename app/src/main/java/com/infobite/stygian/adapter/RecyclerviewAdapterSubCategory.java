package com.infobite.stygian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.infobite.stygian.R;
import com.infobite.stygian.model.category_modal.ProductCategoryList;

public class RecyclerviewAdapterSubCategory extends RecyclerView.Adapter<RecyclerviewAdapterSubCategory.MyViewHolder> {

    List<ProductCategoryList> list;
    Context context;
    private View.OnClickListener onClickListener;

    public RecyclerviewAdapterSubCategory(List<ProductCategoryList> list, Context context, View.OnClickListener onClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_subcate_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductCategoryList productDetail = list.get(position);
        holder.tvItem.setText(productDetail.getName());

        if (productDetail.getCategoryImage() != null) {
            Picasso.with(context)
                    .load(productDetail.getCategoryImage().getSrc())
                    .placeholder(R.drawable.cart_default_img)
                    .error(R.drawable.cart_default_img)
                    .into(holder.imgItem);
        }

        holder.rlItems.setTag(position);
        holder.rlItems.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;
        private ImageView imgItem;
        private RelativeLayout rlItems;

        public MyViewHolder(View view) {
            super(view);
            tvItem = view.findViewById(R.id.tvItem);
            imgItem = view.findViewById(R.id.imgItem);
            rlItems = view.findViewById(R.id.rlItems);
        }
    }

}
