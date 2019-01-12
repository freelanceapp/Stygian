package infobite.technology.stygian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import infobite.technology.stygian.R;
import infobite.technology.stygian.model.SubCategory;

public class AdapterSubCategory extends BaseAdapter {
    Context ctx;
    ArrayList<SubCategory> list;

    public AdapterSubCategory(Context ctx, ArrayList<SubCategory> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myview = inflater.inflate(R.layout.adapter_subcate_view, viewGroup, false);
        TextView name_tv = myview.findViewById(R.id.tv_adpsubcate_name);
        name_tv.setText(list.get(i).getName());
        return myview;
    }
}
