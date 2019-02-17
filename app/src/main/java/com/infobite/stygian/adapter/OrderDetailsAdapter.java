package com.infobite.stygian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.infobite.stygian.R;
import com.infobite.stygian.model.OrderDetail;


public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {

    List<OrderDetail> list;
    Context context;

    public OrderDetailsAdapter(Context context, List<OrderDetail> list) {
        this.list = list;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_tv, qty_tv, total_tv;

        public MyViewHolder(View view) {
            super(view);
            name_tv = itemView.findViewById(R.id.tv_adporderdet_name);
            qty_tv = itemView.findViewById(R.id.tv_adporderdet_qty);
            total_tv = itemView.findViewById(R.id.tv_adporderdet_total);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_orderdetailsitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.name_tv.setText("Product Name \n" + list.get(position).getName());
        holder.total_tv.setText("Total  : " + list.get(position).getTotal());
        holder.qty_tv.setText("Price : " + list.get(position).getPrice() + "\n\n"
                + "Qty : " + list.get(position).getQuantity());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
