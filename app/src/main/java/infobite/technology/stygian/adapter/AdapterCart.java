package infobite.technology.stygian.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import infobite.technology.stygian.R;
import infobite.technology.stygian.database.HelperManager;
import infobite.technology.stygian.fragment.CartFragment;
import infobite.technology.stygian.model.CouponModel;
import infobite.technology.stygian.model.ProductDetail;
import infobite.technology.stygian.util.Utility;
import infobite.technology.stygian.util.WebApi;

import static android.content.Context.MODE_PRIVATE;
import static infobite.technology.stygian.activity.MainActivity.cart_count;
import static infobite.technology.stygian.activity.SplashActivity.mypreference;
import static infobite.technology.stygian.adapter.AdapterCart.MyViewHolder.color_tv;
import static infobite.technology.stygian.adapter.AdapterCart.MyViewHolder.size_tv;


public class AdapterCart extends RecyclerView.Adapter<AdapterCart.MyViewHolder> {

    ArrayList<ProductDetail> list;
    Context context;
    HelperManager helperManager;
    Fragment fragment;
    int proposition = 0;
    TextView select_col,select_size;
    RadioGroup color_radiogroup, size_radiogroup;


    public AdapterCart(ArrayList<ProductDetail> list, Context context, CartFragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;
        helperManager = new HelperManager(context);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public static TextView name_tv, size_tv, color_tv, price_tv, qty_tv, tv_adpcart_edit;
        ImageView pro_image_iv, plus_iv, minus_iv;
        LinearLayout cartLayout;
        public MyViewHolder(View view) {
            super(view);
            name_tv = view.findViewById(R.id.tv_adpcart_name);
            size_tv = view.findViewById(R.id.tv_adpcart_size);
            color_tv = view.findViewById(R.id.tv_adpcart_color);
            price_tv = view.findViewById(R.id.tv_adpcart_price);
            qty_tv = view.findViewById(R.id.tv_adpcart_qty);
            cartLayout = view.findViewById(R.id.cartLayout);
            pro_image_iv = view.findViewById(R.id.iv_adpcart_image);
            plus_iv = view.findViewById(R.id.iv_adpcart_plus);
            minus_iv = view.findViewById(R.id.iv_adpcart_minus);
            tv_adpcart_edit = view.findViewById(R.id.tv_adpcart_edit);
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
        size_tv.setText("Size : " + productDetail.getSelected_size());
        holder.color_tv.setText("Color : " + productDetail.getSelected_color());
        holder.qty_tv.setText(list.get(position).getQuantity() + "");

        Picasso.with(context).load(productDetail.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).resize(300, 300).into(holder.pro_image_iv);

        int qty = Integer.parseInt(holder.qty_tv.getText().toString());
        if (qty > 1) {
            holder.minus_iv.setImageResource(R.drawable.ic_minus);
        } else {
            holder.minus_iv.setImageResource(R.drawable.ic_delete);
        }

        holder.tv_adpcart_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 proposition = position;
                Toast.makeText(context,"id" + position,Toast.LENGTH_SHORT).show();
                 showDialog();
            }
        });

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
                    SharedPreferences.Editor editor = context.getSharedPreferences(mypreference, MODE_PRIVATE).edit();
                    editor.putInt("Cart_Number", cart_count);
                    editor.apply();
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

    public void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_product_details);
        select_col = dialog.findViewById(R.id.tv_prodetails_selectcolor);
        select_size = dialog.findViewById(R.id.tv_prodetails_selectsize);
        color_radiogroup = dialog.findViewById(R.id.rg_selectcolor);
        size_radiogroup = dialog.findViewById(R.id.rg_selectsize);
        getdata();
        ImageView proImg = (ImageView) dialog.findViewById(R.id.proImg);
        Picasso.with(context).load(list.get(proposition).getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).resize(300, 300).into(proImg);
        TextView tv_prodetails_price1 = (TextView) dialog.findViewById(R.id.tv_prodetails_price1);
        tv_prodetails_price1.setText(list.get(proposition).getPrice());

        Button bt_close = (Button) dialog.findViewById(R.id.bt_close);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void getdata() {
        //Utility.showLoader(ctx);
        AndroidNetworking.get(WebApi.API_PRODUCT+list.get(proposition).getId())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        //Utility.hideLoader();
                        setResponse(response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        //Utility.hideLoader();
                        Utility.toastView(context, anError.toString());
                    }
                });
    }

    private void setResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    String id = object.getString("id");
                    String name = object.getString("name");

                    Log.e("Name", name);
                    JSONArray attri_array = object.getJSONArray("attributes");

                    getAttibute(attri_array.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


    }

    private void getAttibute(String attributes_array) {
        ArrayList<String> color_list = new ArrayList<>();
        ArrayList<String> size_list = new ArrayList<>();
        try {
            JSONArray attib = new JSONArray(attributes_array);
            if (attib.length() > 0) {
                for (int i = 0; i < attib.length(); i++) {
                    JSONObject object = attib.getJSONObject(i);
                    String name = object.getString("name");
                    if (name.equalsIgnoreCase("Color")) {
                        JSONArray option_array = object.getJSONArray("options");
                        if (option_array.length() > 0) {
                            for (int j = 0; j < option_array.length(); j++) {
                                String option = option_array.getString(j);
                                color_list.add(option);
                            }
                        }
                    } else if (name.equalsIgnoreCase("Size")) {
                        JSONArray option_array = object.getJSONArray("options");
                        if (option_array.length() > 0) {
                            for (int j = 0; j < option_array.length(); j++) {
                                String option = option_array.getString(j);
                                size_list.add(option);
                            }
                        }
                    }
                }
                setAttribute(color_list, size_list);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setAttribute(ArrayList<String> color_list, ArrayList<String> size_list) {
        if (color_list.size() > 0) {
            select_col.setVisibility(View.VISIBLE);
            for (int i = 0; i < color_list.size(); i++) {
                RadioButton textView = new RadioButton(context);
                textView.setText(color_list.get(i));
                textView.setTextSize(16);
                textView.setPadding(10, 5, 10, 5);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 5, 20, 5);
                textView.setLayoutParams(lp);
                textView.setBackgroundResource(R.drawable.x_attribute_bg);
                color_radiogroup.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  textView.setBackgroundResource(R.drawable.x_attribute_selected_bg);
                        String text = textView.getText().toString();
                        //color_tv.setVisibility(View.VISIBLE);
                       // color_tv.setText("Selected Color : " + text);
                        //selected_color = text;
                        Toast.makeText(context,"Color "+text,Toast.LENGTH_SHORT).show();
                        color_tv.setText(""+text);
                    }
                });
            }
        }
        if (size_list.size() > 0) {
            select_size.setVisibility(View.VISIBLE);
            for (int i = 0; i < size_list.size(); i++) {
                RadioButton textView = new RadioButton(context);
                textView.setText(size_list.get(i));
                textView.setTextSize(16);
                textView.setPadding(15, 5, 15, 5);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 5, 20, 0);
                textView.setLayoutParams(lp);
                textView.setBackgroundResource(R.drawable.x_attribute_bg);
                size_radiogroup.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = textView.getText().toString();
                        //size_tv.setVisibility(View.VISIBLE);
                       // selected_size = text;
                       // size_tv.setText("Selected Size : " + text);
                        Toast.makeText(context,"Size "+text,Toast.LENGTH_SHORT).show();
                        size_tv.setText(""+text);
                    }
                });
            }
        }
    }

}
