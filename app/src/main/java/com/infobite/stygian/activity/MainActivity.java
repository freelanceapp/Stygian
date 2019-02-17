package com.infobite.stygian.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.infobite.stygian.R;
import com.infobite.stygian.constant.Constant;
import com.infobite.stygian.fragment.CartFragment;
import com.infobite.stygian.fragment.HomeFragment;
import com.infobite.stygian.fragment.MainFragment;
import com.infobite.stygian.fragment.MyProfileFragment;
import com.infobite.stygian.fragment.WishlistFragment;
import com.infobite.stygian.menu.DrawerAdapter;
import com.infobite.stygian.menu.DrawerItem;
import com.infobite.stygian.menu.SimpleItem;
import com.infobite.stygian.model.token_responce.TokenModel;
import com.infobite.stygian.retrofit_provider.RetrofitApiClient;
import com.infobite.stygian.retrofit_provider.RetrofitService;
import com.infobite.stygian.retrofit_provider.WebResponse;
import com.infobite.stygian.util.Alerts;
import com.infobite.stygian.util.AppPreference;
import com.infobite.stygian.util.ConstantData;
import com.infobite.stygian.util.NetworkDetector;
import com.infobite.stygian.util.SessionManager;
import com.infobite.stygian.util.Utility;
import com.infobite.stygian.util.WebApi;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import retrofit2.Response;

import static com.infobite.stygian.activity.SplashActivity.mypreference;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, View.OnClickListener {

    Context ctx;
    private SlidingRootNav slidingRootNav;
    private static final int NAV_ALL = 0;
    private static final int NAV_MEN = 1;
    private static final int NAV_WOMEN = 2;
    private static final int NAV_KIDS = 3;
    private static final int NAV_HOME = 4;
    private static final int NAV_WISHLIST = 5;
    private static final int NAV_CART = 6;
    private static final int NAV_MYACCOUNT = 7;
    private static final int NAV_OFFER = 8;
    private static final int NAV_POLICY = 9;
    private static final int NAV_EXIT = 10;

    private String[] screenTitles;
    private Drawable[] screenIcons;
    ImageView cart_btn, logo_iv;
    public static TextView tooltext_tv;
    SessionManager sessionManager;
    public static TextView cart_number;
    private ImageView search_btn;
    public static int cart_count = 0;
    String user_id = "0";
    public NetworkDetector networkDetector;
    public RetrofitApiClient retrofitApiClient;
    public String android_id;
    public String f_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initXml(savedInstanceState);
        setHomePage();
    }

    private void initXml(Bundle savedInstanceState) {
        ctx = this;
        SharedPreferences prefs = getSharedPreferences(mypreference, MODE_PRIVATE);
        user_id = prefs.getString("user_id", "0");//"No name defined" is the default value.
        networkDetector = new NetworkDetector(ctx);
        retrofitApiClient = RetrofitService.getRetrofit();
        //f_token = FirebaseInstanceId.getInstance().getToken();
        Log.e("Firebase", "..." + f_token);
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        bannerApi();
        cart_count = AppPreference.getIntegerPreference(ctx, Constant.CART_ITEM_COUNT); //0 is the default value.
        cart_btn = findViewById(R.id.cart_btn);
        logo_iv = findViewById(R.id.iv_main_logo);
        cart_number = findViewById(R.id.cart_number);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tooltext_tv = (TextView) findViewById(R.id.tooltext);
        search_btn = (ImageView) findViewById(R.id.search_btn);
        cart_number.setText("" + cart_count);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentSearch);
            }
        });
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(ctx);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter drawadapter = new DrawerAdapter(Arrays.asList(
                createItemFor(NAV_ALL).setChecked(true),
                createItemFor(NAV_MEN),
                createItemFor(NAV_WOMEN),
                createItemFor(NAV_KIDS),
                createItemFor(NAV_HOME),
                createItemFor(NAV_WISHLIST),
                createItemFor(NAV_CART),
                createItemFor(NAV_MYACCOUNT),
                createItemFor(NAV_OFFER),
                createItemFor(NAV_POLICY),
                createItemFor(NAV_EXIT)));
        drawadapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(drawadapter);
        drawadapter.setSelected(NAV_ALL);

        cart_btn.setOnClickListener(this);
        logo_iv.setOnClickListener(this);
    }

    public void setHomePage() {

        if (getIntent().getExtras() != null) {
            String type = getIntent().getStringExtra("type");
            if (type.equals(ConstantData.CART)) {
                CartFragment cartFragment = new CartFragment(ctx, this);
                Utility.setFragment(cartFragment, ctx, ConstantData.CART);
            }
        } else {
            MainFragment fragment = new MainFragment();
            Utility.setFragment(fragment, ctx, ConstantData.HOME);
        }
    }

    @Override
    public void onItemSelected(int position) {

        if (position == NAV_ALL) {
            MainFragment fragment = new MainFragment();
            Utility.setFragment(fragment, ctx, ConstantData.HOME);
            slidingRootNav.closeMenu();
        } else if (position == NAV_MEN) {
            startActivity(new Intent(ctx, SubCategoryActivity.class)
                    .putExtra(ConstantData.ACTIVITY_ID, WebApi.ID_SUB_MEN)
                    .putExtra(ConstantData.ACTIVITY_TYPE, ConstantData.MEN));
        } else if (position == NAV_WOMEN) {
            startActivity(new Intent(ctx, SubCategoryActivity.class)
                    .putExtra(ConstantData.ACTIVITY_ID, WebApi.ID_SUB_WOMEN)
                    .putExtra(ConstantData.ACTIVITY_TYPE, ConstantData.WOMEN));
        } else if (position == NAV_KIDS) {
            startActivity(new Intent(ctx, ProductsActivity.class)
                    .putExtra(ConstantData.ACTIVITY_ID, WebApi.ID_PRODUCTS_KIDS)
                    .putExtra(ConstantData.ACTIVITY_TYPE, ConstantData.KIDS));
        } else if (position == NAV_HOME) {
            startActivity(new Intent(ctx, ProductsActivity.class)
                    .putExtra(ConstantData.ACTIVITY_ID, WebApi.ID_PRODUCTS_HOME)
                    .putExtra(ConstantData.ACTIVITY_TYPE, ConstantData.HOME));
        } else if (position == NAV_WISHLIST) {
            tooltext_tv.setText(ConstantData.WISHLIST);
            WishlistFragment fragment = new WishlistFragment(ctx);
            Utility.setFragment(fragment, ctx, ConstantData.WISHLIST);
            slidingRootNav.closeMenu();

        } else if (position == NAV_CART) {
            tooltext_tv.setText(ConstantData.CART);
            /*CartFragment fragment = new CartFragment(ctx, this);
            Utility.setFragment(fragment, ctx, ConstantData.CART);*/
            Intent intent = new Intent(MainActivity.this, WalletActivity.class);
            startActivity(intent);
            slidingRootNav.closeMenu();
        } else if (position == NAV_MYACCOUNT) {
            boolean login = AppPreference.getBooleanPreference(ctx, Constant.IS_LOGIN);
            if (!login) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                tooltext_tv.setText(ConstantData.MY_PROFILE);
                MyProfileFragment fragment = new MyProfileFragment(ctx);
                Utility.setFragment(fragment, ctx, ConstantData.MY_PROFILE);
                slidingRootNav.closeMenu();
            }
        } else if (position == NAV_OFFER) {
            tooltext_tv.setText(ConstantData.OFFERS);
           /* CartFragment fragment = new CartFragment(ctx, this);
            Utility.setFragment(fragment, ctx, ConstantData.CART);*/
            Intent intent = new Intent(MainActivity.this, OffersActivity.class);
            startActivity(intent);
            slidingRootNav.closeMenu();

        } else if (position == NAV_POLICY) {
            tooltext_tv.setText(ConstantData.POLICY);
           /* CartFragment fragment = new CartFragment(ctx, this);
            Utility.setFragment(fragment, ctx, ConstantData.CART);*/
            Intent intent = new Intent(MainActivity.this, PolicyActivity.class);
            startActivity(intent);
            slidingRootNav.closeMenu();

        } else if (position == NAV_EXIT) {
            AppPreference.clearAllPreferences(ctx);
            sessionManager.logoutUser(this);
        }
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.colorWhite))
                .withTextTint(color(R.color.colorWhite))
                .withSelectedIconTint(color(R.color.textColorSecondary))
                .withSelectedTextTint(color(R.color.textColorSecondary));
    }

    private String[] loadScreenTitles() {
        boolean login = AppPreference.getBooleanPreference(ctx, Constant.IS_LOGIN);
        if (login) {
            return getResources().getStringArray(R.array.ld_activityScreenTitles);
        } else {
            return getResources().getStringArray(R.array.navigation_item);
        }
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.cart_btn:
                tooltext_tv.setText(ConstantData.CART);
                CartFragment fragment = new CartFragment(ctx, this);
                Utility.setFragment(fragment, ctx, ConstantData.CART);
                break;

            case R.id.iv_main_logo:
                tooltext_tv.setText(ConstantData.HOME);
                HomeFragment allfragment = new HomeFragment();
                Utility.setFragment(allfragment, ctx, ConstantData.HOME);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (slidingRootNav.isMenuOpened()) {
            slidingRootNav.closeMenu();
        } else {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment_byID = fm.findFragmentById(R.id.home_frame);
            String tag = fragment_byID.getTag();
            if (!tag.equals(ConstantData.HOME)) {
                HomeFragment homeFragment = new HomeFragment();
                Utility.setFragment(homeFragment, ctx, ConstantData.HOME);
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cart_count = AppPreference.getIntegerPreference(ctx, Constant.CART_ITEM_COUNT); //0 is the default value.
        cart_number.setText("" + cart_count);
    }

    private void bannerApi() {
        if (networkDetector.isNetworkAvailable()) {
            RetrofitService.getToken(new Dialog(ctx), retrofitApiClient.gettoken(android_id, f_token), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    TokenModel bannerModel = (TokenModel) result.body();
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(ctx, error);
                }
            });
        } else {
            networkDetector.show(ctx);
        }
    }
}


