package infobite.technology.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import infobite.technology.stygian.R;
import infobite.technology.stygian.adapter.ProductAdapter;
import infobite.technology.stygian.model.ProductDetail;
import infobite.technology.stygian.util.ConnectionDetector;
import infobite.technology.stygian.util.Constant;
import infobite.technology.stygian.util.Utility;
import infobite.technology.stygian.util.WebApi;

import static infobite.technology.stygian.fragment.MensFragment.related_ids;

@SuppressLint("ValidFragment")
public class MobileFragment extends Fragment implements View.OnClickListener {

    Context ctx;
    String type;

    RecyclerView recyclerView;
    Button load_bt;
    ConnectionDetector connectionDetector;
    static int page = 0;
    String api = "";

    ArrayList<ProductDetail> list;

    @SuppressLint("ValidFragment")
    public MobileFragment() {
    }

    public MobileFragment(Context ctx, String type) {
        this.ctx = ctx;
        this.type = type;
        list = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recyclerview, container, false);
        initXml(view);
        boolean internet = connectionDetector.isConnected();
        if (internet) {
            if (type.equals(Constant.MOBILE)) {
                api = WebApi.API_MOBILE;
            }
            getdata();
        } else {
            Utility.toastView(ctx, ctx.getString(R.string.no_internet));
        }
        return view;
    }

    public void getdata() {
        int pos = list.size() - 1;
        page++;
        //Utility.showLoader(ctx);
        AndroidNetworking.get(api + page)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Utility.hideLoader();
                        setResponse(response, pos);
                    }

                    @Override
                    public void onError(ANError anError) {
                        //Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

    private void setResponse(JSONArray response, int pos) {
        ArrayList<String> productLink = new ArrayList<>();
        if (response.length() > 0) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String id = object.getString("id");
                    String name = object.getString("name");
                    String permalink = object.getString("permalink");
                    productLink.add(permalink);
                    float price = object.getLong("price");
                    int roundprice = Math.round(price);
                    String reg_price = object.getString("regular_price");
                    String sale_price = object.getString("sale_price");
                    String html_price = object.getString("price_html");
                    String description = object.getString("description");
                    JSONArray image_array = object.getJSONArray("images");
                    related_ids = object.getJSONArray("related_ids");

                    String image = "";
                    if (image_array.length() > 0) {
                        JSONObject objectimg = image_array.getJSONObject(0);
                        image = objectimg.getString("src");
                    }
                    JSONArray attri_array = object.getJSONArray("attributes");
                    list.add(new ProductDetail(id, name, description, String.valueOf(roundprice), reg_price,
                            sale_price, html_price, image, image_array.toString(), attri_array.toString(), 1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (list.size() > 0) {
                setAdapter(list, pos, productLink);
            }
        }
    }

    private void setAdapter(ArrayList<ProductDetail> list, int pos, ArrayList<String> productLink) {
        ProductAdapter adapter = new ProductAdapter(list, ctx, productLink);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(pos);
    }

    private void initXml(View view) {
        recyclerView = view.findViewById(R.id.layout_recyclerview);
        load_bt = view.findViewById(R.id.bt_layout_loadmore);
        load_bt.setVisibility(View.VISIBLE);

        load_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        getdata();
    }
}
