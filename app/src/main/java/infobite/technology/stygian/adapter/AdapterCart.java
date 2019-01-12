package infobite.technology.stygian.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import infobite.technology.stygian.R;
import infobite.technology.stygian.database.HelperManager;
import infobite.technology.stygian.fragment.CartFragment;
import infobite.technology.stygian.model.ProductDetail;


public class AdapterCart extends RecyclerView.Adapter<AdapterCart.MyViewHolder> {

    ArrayList<ProductDetail> list;
    Context context;
    HelperManager helperManager;
    Fragment fragment;

    public AdapterCart(ArrayList<ProductDetail> list, Context context, CartFragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;
        helperManager = new HelperManager(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_tv, size_tv, color_tv, price_tv, qty_tv;
        ImageView pro_image_iv, plus_iv, minus_iv;

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
        holder.size_tv.setText("Size : " + productDetail.getSelected_size());
        holder.color_tv.setText("Color : " + productDetail.getSelected_color());
        holder.qty_tv.setText(list.get(position).getQuantity() + "");

        Picasso.with(context).load(productDetail.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).resize(300, 300).into(holder.pro_image_iv);

        int qty = Integer.parseInt(holder.qty_tv.getText().toString());
        if (qty > 1) {
            holder.minus_iv.setImageResource(R.drawable.ic_minus);
        } else {
            holder.minus_iv.setImageResource(R.drawable.ic_delete);
        }

        holder.plus_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(holder.qty_tv.getText().toString());
                qty++;
                helperManager.updateCart(qty, list.get(position).getId());
                holder.qty_tv.setText(qty + "");
                ((CartFragment) fragment).setTotal();
                if (qty > 1) {
                    holder.minus_iv.setImageResource(R.drawable.ic_minus);
                } else {
                    holder.minus_iv.setImageResource(R.drawable.ic_delete);
                }
            }
        });
        holder.minus_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(holder.qty_tv.getText().toString());
                if (qty == 1) {
                    helperManager.deletesingleCart(list.get(position));
                    list.remove(position);
                    notifyDataSetChanged();
                } else {
                    qty--;
                    helperManager.updateCart(qty, list.get(position).getId());
                    holder.qty_tv.setText(qty + "");
                }
                if (qty > 1) {
                    holder.minus_iv.setImageResource(R.drawable.ic_minus);
                } else {
                    holder.minus_iv.setImageResource(R.drawable.ic_delete);
                }
                ((CartFragment) fragment).setTotal();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
