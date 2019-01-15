package infobite.technology.stygian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import infobite.technology.stygian.R;
import infobite.technology.stygian.activity.ProductDetailsActivity;
import infobite.technology.stygian.database.HelperManager;
import infobite.technology.stygian.model.ProductDetail;


/**
 * Created by amar on 7/23/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<ProductDetail> list;
    private ArrayList<String> productLink;
    Context ctx;
    HelperManager helperManager;
    ArrayList<String> fav_id_list;

    public ProductAdapter(List<ProductDetail> moviesList, Context context, ArrayList<String> productLink) {
        this.list = moviesList;
        this.productLink = productLink;
        this.ctx = context;
        helperManager = new HelperManager(ctx);
        fav_id_list = helperManager.readAllWishlistID();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_name, product_price, old_price;
        ImageView product_img, product_fave;

        public MyViewHolder(View view) {
            super(view);
            product_name = view.findViewById(R.id.product_name);
            product_price = view.findViewById(R.id.product_price);
            product_img = view.findViewById(R.id.product_img);
            product_fave = view.findViewById(R.id.product_fave);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ProductDetail product = list.get(position);
        holder.product_name.setText(product.getName());
        float price = Float.parseFloat(product.getPrice());
        int roun_price = Math.round(price);
        holder.product_price.setText("₹ " + roun_price + "");

        Picasso.with(ctx).load(product.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(holder.product_img);

        if (fav_id_list.contains(product.getId())) {
            holder.product_fave.setImageResource(R.drawable.fav_enable);
        } else {
            holder.product_fave.setImageResource(R.drawable.fav_disable);
        }

        holder.product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, ProductDetailsActivity.class);
                intent.putExtra("data", list.get(position));
                intent.putExtra("link", productLink.get(position));
                ctx.startActivity(intent);
            }
        });

        holder.product_fave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav_id_list.contains(product.getId())) {
                    holder.product_fave.setImageResource(R.drawable.fav_disable);
                    helperManager.deletesingleWishlist(product);
                    fav_id_list.remove(product.getId());
                } else {
                    helperManager.insertWishlist(product);
                    fav_id_list.add(product.getId());
                    holder.product_fave.setImageResource(R.drawable.fav_enable);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
