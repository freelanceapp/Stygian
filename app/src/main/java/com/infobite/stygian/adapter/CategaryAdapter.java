package com.infobite.stygian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infobite.stygian.R;
import com.infobite.stygian.model.Category;

import java.util.ArrayList;


public class CategaryAdapter extends RecyclerView.Adapter<CategaryAdapter.MyViewHolder> {

    public static String cat_id1 = "33";
    private LayoutInflater inflater;
    public static ArrayList<Category> imageModelArrayList;
    public Context ctx;
    public CategaryAdapter(Context ctx, ArrayList<Category> imageModelArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_category, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.item_catname.setText(imageModelArrayList.get(position).getCname());

        holder.item_catname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(ctx,imageModelArrayList.get(position).getCname(),Toast.LENGTH_SHORT).show();
//                MensFragment fragment = new MensFragment(ctx);
//                FragmentManager fragmentManager = ((AppCompatActivity) ctx).getSupportFragmentManager();
//                Bundle bundle=new Bundle();
                //bundle.putString("c_id", imageModelArrayList.get(position).getCid());
                //cat_id1 = imageModelArrayList.get(position).getCid();
                //set Fragmentclass Arguments
                //fragment.setArguments(bundle);
//                fragmentManager.beginTransaction().replace(R.id.container2, fragment).addToBackStack("0").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView item_catname,item_like_count,item_comment_count;


        public MyViewHolder(View itemView) {
            super(itemView);
            item_catname = (TextView) itemView.findViewById(R.id.item_catname);

        }

    }

}
