package com.infobite.stygian.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.infobite.stygian.R;
import com.infobite.stygian.model.all_product_modal.AllProductMainModal;

public class AllProductListAdapter extends ArrayAdapter<AllProductMainModal> {

    private List<AllProductMainModal> originalList;
    private List<AllProductMainModal> DatumList;
    private DatumFilter filter;
    private Context context;

    public AllProductListAdapter(Context context, int textViewResourceId, List<AllProductMainModal> DatumList) {
        super(context, textViewResourceId, DatumList);
        this.DatumList = new ArrayList<>();
        this.DatumList.addAll(DatumList);
        this.originalList = new ArrayList<>();
        this.originalList.addAll(DatumList);
        this.context = context;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new DatumFilter();
        }
        return filter;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));
        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (vi != null) {
                convertView = vi.inflate(R.layout.row_product_item, null);
            }
            holder = new ViewHolder();
            if (convertView != null) {
                holder.name_tv = convertView.findViewById(R.id.tv_adpcart_name);
                holder.size_tv = convertView.findViewById(R.id.tv_adpcart_size);
                holder.color_tv = convertView.findViewById(R.id.tv_adpcart_color);
                holder.price_tv = convertView.findViewById(R.id.tv_adpcart_price);
                holder.qty_tv = convertView.findViewById(R.id.tv_adpcart_qty);

                holder.pro_image_iv = convertView.findViewById(R.id.iv_adpcart_image);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AllProductMainModal Datum = DatumList.get(position);
        holder.name_tv.setText(Datum.getName());
        holder.price_tv.setText("₹ " + Datum.getPrice());

        if (Datum.getImages().size() > 0) {
            String strUrl = Datum.getImages().get(0).getSrc();
            Picasso.with(context)
                    .load(strUrl)
                    .placeholder(R.drawable.cart_default_img)
                    .error(R.drawable.cart_default_img)
                    .resize(300, 300)
                    .into(holder.pro_image_iv);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView name_tv, size_tv, color_tv, price_tv, qty_tv;
        ImageView pro_image_iv, plus_iv, minus_iv;
        RelativeLayout ll_adpcart_action;
    }

    private class DatumFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint != null && constraint.toString().length() > 0) {
                ArrayList<AllProductMainModal> filteredItems = new ArrayList<AllProductMainModal>();

                for (int i = 0, l = originalList.size(); i < l; i++) {
                    AllProductMainModal Datum = originalList.get(i);
                    if (Datum.getName().toLowerCase().contains(constraint))
                        filteredItems.add(Datum);
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            } else {
                synchronized (this) {
                    result.values = originalList;
                    result.count = originalList.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            DatumList = (ArrayList<AllProductMainModal>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = DatumList.size(); i < l; i++)
                add(DatumList.get(i));
            notifyDataSetInvalidated();
        }
    }
}
