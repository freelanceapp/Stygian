package infobite.technology.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import static infobite.technology.stygian.fragment.MensFragment.related_ids;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import infobite.technology.stygian.R;
import infobite.technology.stygian.activity.ProductsActivity;
import infobite.technology.stygian.activity.SubCategoryActivity;
import infobite.technology.stygian.adapter.MarriagePagerAdapter;
import infobite.technology.stygian.adapter.ProductAdapter;
import infobite.technology.stygian.adapter.SlidingImage_Adapter;
import infobite.technology.stygian.adapter.SlidingImage_Adapter1;
import infobite.technology.stygian.model.ProductDetail;
import infobite.technology.stygian.util.ConnectionDetector;
import infobite.technology.stygian.util.Constant;
import infobite.technology.stygian.util.Utility;
import infobite.technology.stygian.util.WebApi;


@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements View.OnClickListener {

    Context ctx;
    LinearLayout men_ll, women_ll, home_ll;
    RecyclerView recyclerView;
    CirclePageIndicator indicator;
    ConnectionDetector connectionDetector;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.banner1,R.drawable.img2,R.drawable.img1,R.drawable.img2};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    public HomeFragment(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initXml(view);

        boolean internet = connectionDetector.isConnected();
        if (internet) {
            getData();
        } else {
            Utility.toastView(ctx, ctx.getString(R.string.no_internet));
        }
        return view;
    }

    private void getData() {
        //Utility.showLoader(ctx);
        AndroidNetworking.get(WebApi.API_MENS + "1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                       // Utility.hideLoader();
                        setResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                       // Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

    private void setResponse(JSONArray response) {

        ArrayList<ProductDetail> list = new ArrayList<>();
        if (response.length() > 0) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String id = object.getString("id");
                    String name = object.getString("name");
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
                    list.add(new ProductDetail(id, name,description, String.valueOf(roundprice), reg_price,
                            sale_price, html_price, image, image_array.toString(), attri_array.toString(), 1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (list.size() > 0) {
                setAdapter(list);
            }
        }
    }

    private void init() {
        for(int i=0;i<IMAGES.length;i++) ImagesArray.add(IMAGES[i]);
        mPager.setAdapter(new SlidingImage_Adapter1(ctx,ImagesArray));
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(3 * density);
        NUM_PAGES =IMAGES.length;
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }
            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }
            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    private void setAdapter(ArrayList<ProductDetail> list) {
        ProductAdapter adapter = new ProductAdapter(list, ctx);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    private void initXml(View view) {
        men_ll = view.findViewById(R.id.ll_home_men);
        women_ll = view.findViewById(R.id.ll_home_women);
        home_ll = view.findViewById(R.id.ll_home_home);
        recyclerView = view.findViewById(R.id.rv_home_recyclerview);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        men_ll.setOnClickListener(this);
        women_ll.setOnClickListener(this);
        home_ll.setOnClickListener(this);
        init();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ll_home_men:
                startActivity(new Intent(ctx, SubCategoryActivity.class)
                        .putExtra(Constant.ACTIVITY_ID, WebApi.ID_SUB_MEN)
                        .putExtra(Constant.ACTIVITY_TYPE, Constant.MEN));
                break;

            case R.id.ll_home_women:
                startActivity(new Intent(ctx, SubCategoryActivity.class)
                        .putExtra(Constant.ACTIVITY_ID, WebApi.ID_SUB_WOMEN)
                        .putExtra(Constant.ACTIVITY_TYPE, Constant.WOMEN));
                break;

            case R.id.ll_home_home:
                startActivity(new Intent(ctx, ProductsActivity.class)
                        .putExtra(Constant.ACTIVITY_ID, WebApi.ID_PRODUCTS_HOME)
                        .putExtra(Constant.ACTIVITY_TYPE, Constant.HOME));
                break;
        }
    }
}
