package infobite.technology.stygian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import infobite.technology.stygian.R;
import infobite.technology.stygian.activity.OrderDetailsActivity;
import infobite.technology.stygian.model.AddtoCard;
import infobite.technology.stygian.model.Orders;

import java.util.List;


public class OrderItermAdapter extends RecyclerView.Adapter<OrderItermAdapter.MyViewHolder> {

    List<Orders> list;
    Context context;

    public OrderItermAdapter(Context context, List<Orders> list) {
        this.list = list;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_tv, status_tv, total_tv;

        public MyViewHolder(View view) {
            super(view);
            id_tv = itemView.findViewById(R.id.tv_adporder_id);
            status_tv = itemView.findViewById(R.id.tv_adporder_status);
            total_tv = itemView.findViewById(R.id.tv_adporder_total);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_orderitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.id_tv.setText("Order Id : " + list.get(position).getId());
        holder.total_tv.setText("Total Cart : " + list.get(position).getTotal());
        holder.status_tv.setText("Order Status : " + list.get(position).getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, OrderDetailsActivity.class)
                .putExtra("data",list.get(position).getItem_array()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
