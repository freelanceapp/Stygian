package com.infobite.stygian.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.infobite.stygian.R;
import com.infobite.stygian.adapter.ProductAdapter;
import com.infobite.stygian.adapter.SlidingImage_Adapter;
import com.infobite.stygian.constant.Constant;
import com.infobite.stygian.database.DatabaseHandler;
import com.infobite.stygian.database.HelperManager;
import com.infobite.stygian.model.ProductDetail;
import com.infobite.stygian.util.Alerts;
import com.infobite.stygian.util.AppPreference;
import com.infobite.stygian.util.ConstantData;
import com.infobite.stygian.util.Utility;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.infobite.stygian.activity.MainActivity.cart_count;
import static com.infobite.stygian.fragment.MensFragment.related_ids;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<ProductDetail> list;

    Context ctx;
    ImageView back_iv, cart_iv;
    TextView title_tv, name_tv, price_tv, select_col, select_size, size_tv, color_tv, cart_number, moreBtn;
    Button wishlist_bt, addcart_bt;
    RadioGroup color_radiogroup, size_radiogroup;
    CirclePageIndicator indicator;
    private static ViewPager mPager;
    HelperManager helperManager;
    ArrayList<String> wish_id_list;
    ArrayList<String> cart_id_list;
    String selected_size = "", selected_color = "";
    WebView product_detail1;
    String w_details;
    ProductDetail productDetail;
    ImageView viewBtn;
    RecyclerView recommendList;
    String report_id, strProductLink = "";

    private String DATABASE_CART = "cart.db";
    private String DATABASE_WISHLIST = "wishlist.db";
    private DatabaseHandler databaseCart, databaseWishlist;
    private ArrayList<ProductDetail> cartProductList = new ArrayList<>();
    private ArrayList<ProductDetail> wishlistProductList = new ArrayList<>();

    private ArrayList<String> color_list;
    private ArrayList<String> size_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ctx = this;

        cart_count = AppPreference.getIntegerPreference(ctx, Constant.CART_ITEM_COUNT); //0 is the default value.
        initDatabase();
        initXml();
        getData();
    }

    private void initDatabase() {
        productDetail = getIntent().getExtras().getParcelable("data");
        databaseCart = new DatabaseHandler(ctx, DATABASE_CART);
        databaseWishlist = new DatabaseHandler(ctx, DATABASE_WISHLIST);
        cartProductList.clear();
        if (databaseCart.getContactsCount()) {
            cartProductList = databaseCart.getAllUrlList();
        }
        if (databaseWishlist.getContactsCount()) {
            wishlistProductList = databaseWishlist.getAllUrlList();
        }
    }

    private void initXml() {
        ctx = this;
        list = new ArrayList<>();
        back_iv = findViewById(R.id.iv_prodetails_back);
        cart_number = findViewById(R.id.cart_number);
        cart_iv = findViewById(R.id.iv_prodetails_cart);
        title_tv = findViewById(R.id.tv_prodetails_title);
        name_tv = findViewById(R.id.tv_prodetails_name);
        price_tv = findViewById(R.id.tv_prodetails_price);
        size_tv = findViewById(R.id.tv_prodetails_size);
        color_tv = findViewById(R.id.tv_prodetails_color);
        select_col = findViewById(R.id.tv_prodetails_selectcolor);
        select_size = findViewById(R.id.tv_prodetails_selectsize);
        wishlist_bt = findViewById(R.id.bt_prodetails_wishlist);
        addcart_bt = findViewById(R.id.bt_prodetails_addcart);
        indicator = findViewById(R.id.ci_prodetails_circle);
        mPager = findViewById(R.id.vp_prodetails_viewpager);
        color_radiogroup = findViewById(R.id.rg_selectcolor);
        size_radiogroup = findViewById(R.id.rg_selectsize);
        product_detail1 = findViewById(R.id.product_detail);
        recommendList = findViewById(R.id.recommendList);
        moreBtn = findViewById(R.id.moreBtn);
        moreBtn.setOnClickListener(this);
        helperManager = new HelperManager(ctx);
        back_iv.setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgShare)).setOnClickListener(this);
        wishlist_bt.setOnClickListener(this);
        addcart_bt.setOnClickListener(this);
        cart_iv.setOnClickListener(this);
        cart_id_list = new ArrayList<>();
        wish_id_list = new ArrayList<>();
        WebSettings settings = product_detail1.getSettings();
        settings.setMinimumFontSize(18);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        product_detail1.setWebChromeClient(new WebChromeClient());
        cart_number.setText("" + cart_count);

        viewBtn = findViewById(R.id.viewBtn);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        for (int i = 0; i < related_ids.length(); i++) {
            try {
                Log.e("Recomments Product ", "..." + related_ids.get(i).toString());
                report_id = related_ids.get(i).toString();
                getdata1();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getData() {
        strProductLink = getIntent().getStringExtra("link");
        title_tv.setText(productDetail.getName());
        name_tv.setText(productDetail.getName());
        float price = Float.parseFloat(productDetail.getPrice());
        float round_pr = Math.round(price);
        w_details = productDetail.getDescription();
        product_detail1.loadDataWithBaseURL(null, w_details, "text/html", "UTF-8", null);
        price_tv.setText("₹ " + round_pr);
        getAttibute(productDetail.getAttributes_array());
        init(productDetail.getImages_array());
    }

    private void getAttibute(String attributes_array) {
        color_list = new ArrayList<>();
        size_list = new ArrayList<>();
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
                RadioButton textView = new RadioButton(this);
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
                        color_tv.setVisibility(View.VISIBLE);
                        color_tv.setText("Selected Color : " + text);
                        selected_color = text;
                    }
                });
            }
        }
        if (size_list.size() > 0) {
            select_size.setVisibility(View.VISIBLE);
            for (int i = 0; i < size_list.size(); i++) {
                RadioButton textView = new RadioButton(this);
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
                        size_tv.setVisibility(View.VISIBLE);
                        selected_size = text;
                        size_tv.setText("Selected Size : " + text);
                    }
                });
            }
        }
    }

    private void init(String images_array) {
        ArrayList<String> img_list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(images_array);
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objectimg = array.getJSONObject(i);
                    String image = objectimg.getString("src");
                    img_list.add(image);
                }
                mPager.setAdapter(new SlidingImage_Adapter(ctx, img_list));
                indicator.setViewPager(mPager);
                final float density = getResources().getDisplayMetrics().density;
                indicator.setRadius(5 * density);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgShare:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                i.putExtra(Intent.EXTRA_TEXT, strProductLink);
                startActivity(Intent.createChooser(i, "Share URL"));
                break;
            case R.id.iv_prodetails_back:
                finish();
                break;
            case R.id.bt_prodetails_addcart:
                if (color_list.size() > 0 || size_list.size() > 0) {

                    if (color_list.size() > 0) {
                        if (selected_color.equals("")) {
                            Utility.toastView(ctx, "Select color");
                        } else {
                            if (size_list.size() > 0) {
                                if (selected_size.equals("")) {
                                    Utility.toastView(ctx, "Select size");
                                } else {
                                    addtoCart();
                                }
                            } else {
                                addtoCart();
                            }
                        }
                    } else if (size_list.size() > 0) {
                        if (selected_size.equals("")) {
                            Utility.toastView(ctx, "Select size");
                        } else {
                            if (color_list.size() > 0) {
                                if (selected_color.equals("")) {
                                    Utility.toastView(ctx, "Select color");
                                } else {
                                    addtoCart();
                                }
                            } else {
                                addtoCart();
                            }
                        }
                    }
                } else {
                    addtoCart();
                }

                break;
            case R.id.bt_prodetails_wishlist:
                addtoWishlist();
                break;
            case R.id.iv_prodetails_cart:
                finishAffinity();
                startActivity(new Intent(ctx, MainActivity.class)
                        .putExtra("type", ConstantData.CART));
                break;
            case R.id.moreBtn:
                Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void addtoWishlist() {
        wish_id_list = helperManager.readAllWishlistID();
        //ProductDetail productDetail = getIntent().getExtras().getParcelable("data");
        if (wish_id_list.contains(productDetail.getId())) {
            Utility.toastView(ctx, "Already Added to Wishlist");
        } else {
            boolean insert = helperManager.insertWishlist(productDetail);
            if (insert) {
                Utility.toastView(ctx, "Add to Wishlist");
            }
        }
        /*********************************************************************************************************/
       /* if (databaseWishlist.getContactsCount()) {
            wishlistProductList.addAll(databaseWishlist.getAllUrlList());
        }
        if (wishlistProductList.size() > 0) {
            if (databaseWishlist.verification(productDetail.getId())) {
                Alerts.show(ctx, "Already added to Wishlist");
            } else {
                Alerts.show(ctx, "Added to Wishlist");
                databaseWishlist.addItemCart(productDetail);
            }
        } else {
            Alerts.show(ctx, "Added to Wishlist");
            databaseWishlist.addItemCart(productDetail);
        }*/
    }

    private void addtoCart() {
        /*********************************************************************************************************/
        if (databaseCart.getContactsCount()) {
            cartProductList = databaseCart.getAllUrlList();
        }

        if (cartProductList.size() > 2) {
            Alerts.show(ctx, "Cart full");
        } else {
            if (cartProductList.size() > 0) {
                if (databaseCart.verification(productDetail.getId())) {
                    Alerts.show(ctx, "Already added to Cart");
                } else {
                    productDetail.setSelected_size(selected_size);
                    productDetail.setSelected_color(selected_color);
                    cart_count = cart_count + 1;
                    cart_number.setText("" + cart_count);
                    AppPreference.setIntegerPreference(ctx, Constant.CART_ITEM_COUNT, cart_count);
                    Alerts.show(ctx, "Added to Cart");
                    databaseCart.addItemCart(productDetail);
                }
            } else {
                productDetail.setSelected_size(selected_size);
                productDetail.setSelected_color(selected_color);
                cart_count = cart_count + 1;
                cart_number.setText("" + cart_count);
                AppPreference.setIntegerPreference(ctx, Constant.CART_ITEM_COUNT, cart_count);
                Alerts.show(ctx, "Added to Cart");
                databaseCart.addItemCart(productDetail);
            }
        }
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        CirclePageIndicator indicator = dialog.findViewById(R.id.ci_prodetails_circle);
        ViewPager mPager = dialog.findViewById(R.id.vp_prodetails_viewpager);

        ArrayList<String> img_list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(productDetail.getImages_array());
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objectimg = array.getJSONObject(i);
                    String image = objectimg.getString("src");
                    img_list.add(image);
                }
                mPager.setAdapter(new SlidingImage_Adapter(ctx, img_list));
                indicator.setViewPager(mPager);
                final float density = getResources().getDisplayMetrics().density;
                indicator.setRadius(5 * density);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.closeBtn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void getdata1() {
        AndroidNetworking.get("https://stygianstore.com/wp-json/wc/v3/products/" + report_id)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        setResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

    private void setResponse(String response) {
        ArrayList<String> productLink = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(response);
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

            if (list.size() > 0) {
                setAdapter(list, productLink);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(ArrayList<ProductDetail> list, ArrayList<String> productLink) {
        ProductAdapter adapter = new ProductAdapter(list, ctx, productLink);
        GridLayoutManager layoutManager = new GridLayoutManager(ctx, 2);
        recommendList.setLayoutManager(layoutManager);
        recommendList.setItemAnimator(new DefaultItemAnimator());
        recommendList.setAdapter(adapter);
    }
}
