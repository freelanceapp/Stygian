package com.infobite.stygian.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import com.infobite.stygian.R;
import com.infobite.stygian.model.SaveAddress;
import com.infobite.stygian.util.SessionManager;

public class SaveAddressAdapter extends RecyclerView.Adapter<SaveAddressAdapter.MyViewHolder> {

    Context ctx;
    ArrayList<SaveAddress> address_list;

    SessionManager sessionManager;

    public SaveAddressAdapter(Context ctx, ArrayList<SaveAddress> address_list) {
        this.ctx = ctx;
        this.address_list = address_list;
        sessionManager = new SessionManager(ctx);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adp_saveaddress, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.title_tv.setText("Address" + position + 1);
        String address1 = address_list.get(position).getAddress() + "," + address_list.get(position).getCity();
        String address2 = address_list.get(position).getState() + "," + address_list.get(position).getZipcode();
        holder.address1_tv.setText(address1);
        holder.address2_tv.setText(address2);
        int address_no = position + 1;
        holder.title_tv.setText("Address - " + address_no);


        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return address_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title_tv, address1_tv, address2_tv, edit_tv;
        RadioButton radioButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.tv_adpsave_title);
            address1_tv = itemView.findViewById(R.id.tv_adpsave_address1);
            address2_tv = itemView.findViewById(R.id.tv_adpsave_address2);
            edit_tv = itemView.findViewById(R.id.tv_adpsave_edit);
            radioButton = itemView.findViewById(R.id.rb_adpsaveaddress);
        }
    }
}
