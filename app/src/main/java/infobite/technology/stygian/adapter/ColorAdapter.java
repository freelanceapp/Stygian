package infobite.technology.stygian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import infobite.technology.stygian.R;
import infobite.technology.stygian.model.ColorModel;

import java.util.ArrayList;


public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    public static ArrayList<ColorModel> colorModelArrayList;
    public Context ctx;
    public ColorAdapter(Context ctx, ArrayList<ColorModel> colorModelArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.colorModelArrayList = colorModelArrayList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_color, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.item_color.setImageResource(colorModelArrayList.get(position).getColor());

        holder.item_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx,colorModelArrayList.get(position).getColor(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_color;


        public MyViewHolder(View itemView) {
            super(itemView);
            item_color = (ImageView) itemView.findViewById(R.id.item_color);

        }

    }

}
