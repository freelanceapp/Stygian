package infobite.technology.stygian.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;

import java.util.ArrayList;

import infobite.technology.stygian.R;
import infobite.technology.stygian.adapter.AdapterSubCategory;
import infobite.technology.stygian.model.SubCategory;
import infobite.technology.stygian.util.ConnectionDetector;
import infobite.technology.stygian.util.Constant;
import infobite.technology.stygian.util.JsonParser;
import infobite.technology.stygian.util.Utility;
import infobite.technology.stygian.util.WebApi;

public class SubCategoryActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Context ctx;
    ListView listView;
    ImageView back_iv;
    TextView title_tv;

    ArrayList<SubCategory> list;
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        initXml();
        boolean internet = connectionDetector.isConnected();
        if (internet) {
            String id = getIntent().getStringExtra(Constant.ACTIVITY_ID);
            getData(id);
        } else {
            Utility.toastView(ctx, ctx.getString(R.string.no_internet));
        }
    }

    private void getData(String id) {
        Utility.showLoader(ctx);
        AndroidNetworking.get(WebApi.API_SUB_CATEGORY + id)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Utility.hideLoader();
                        list = JsonParser.getSubCategory(response);
                        AdapterSubCategory adapterSubCategory = new AdapterSubCategory(ctx, list);
                        listView.setAdapter(adapterSubCategory);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

    private void initXml() {
        ctx = this;
        listView = findViewById(R.id.lv_subcate_listview);
        back_iv = findViewById(R.id.iv_subcate_back);
        title_tv = findViewById(R.id.tv_subcate_title);

        title_tv.setText(getIntent().getStringExtra(Constant.ACTIVITY_TYPE));
        back_iv.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        startActivity(new Intent(ctx, ProductsActivity.class)
                .putExtra(Constant.ACTIVITY_ID, list.get(i).getId())
                .putExtra(Constant.ACTIVITY_TYPE, list.get(i).getName()));
    }
}
