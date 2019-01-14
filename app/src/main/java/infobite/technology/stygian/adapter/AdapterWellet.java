package infobite.technology.stygian.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import infobite.technology.stygian.model.wallet_responce.WalletModel;

import static android.content.Context.MODE_PRIVATE;
import static infobite.technology.stygian.activity.MainActivity.cart_count;
import static infobite.technology.stygian.activity.SplashActivity.mypreference;


public class AdapterWellet extends RecyclerView.Adapter<AdapterWellet.MyViewHolder> {

    ArrayList<WalletModel> list;
    Context context;
    HelperManager helperManager;
    Fragment fragment;

    public AdapterWellet(ArrayList<WalletModel> list, Context context) {
        this.list = list;
        this.context = context;
        helperManager = new HelperManager(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView wallet_detail, wallet_date, wallet_price;
        ImageView wallet_img;

        public MyViewHolder(View view) {
            super(view);
            wallet_detail = view.findViewById(R.id.wallet_detail);
            wallet_date = view.findViewById(R.id.wallet_date);

            wallet_img = view.findViewById(R.id.wallet_img);
            wallet_price = view.findViewById(R.id.wallet_price);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_wallet, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WalletModel productDetail = list.get(position);
        holder.wallet_detail.setText(productDetail.getDetails());
        holder.wallet_date.setText(productDetail.getDate());
        holder.wallet_price.setText(productDetail.getAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
