package com.infobite.stygian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.infobite.stygian.R;
import com.infobite.stygian.activity.ProductsActivity;
import com.infobite.stygian.model.SubCategory;
import com.infobite.stygian.util.ConstantData;


public class SubCategaryAdapter1 extends RecyclerView.Adapter<SubCategaryAdapter1.MyViewHolder> {

    private LayoutInflater inflater;
    public static ArrayList<SubCategory> imageModelArrayList;
    public Context ctx;

    public SubCategaryAdapter1(Context ctx, ArrayList<SubCategory> imageModelArrayList) {
        this.imageModelArrayList = imageModelArrayList;
        this.ctx = ctx;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_subcate_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_adpsubcate_name.setText(imageModelArrayList.get(position).getName());

        holder.tv_adpsubcate_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, ProductsActivity.class)
                        .putExtra(ConstantData.ACTIVITY_ID, imageModelArrayList.get(position).getId())
                        .putExtra(ConstantData.ACTIVITY_TYPE, imageModelArrayList.get(position).getName()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_adpsubcate_name, item_like_count, item_comment_count;


        public MyViewHolder(View itemView) {
            super(itemView);
            // tv_adpsubcate_name = (TextView) itemView.findViewById(R.id.tv_adpsubcate_name);

        }

    }

}
