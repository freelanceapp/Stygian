package infobite.technology.stygian.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import infobite.technology.stygian.R;
import infobite.technology.stygian.fragment.HomeFragment;
import infobite.technology.stygian.fragment.MainFragment;
import infobite.technology.stygian.fragment.CartFragment;
import infobite.technology.stygian.fragment.MyProfileFragment;
import infobite.technology.stygian.fragment.WishlistFragment;
import infobite.technology.stygian.menu.DrawerAdapter;
import infobite.technology.stygian.menu.DrawerItem;
import infobite.technology.stygian.menu.SimpleItem;
import infobite.technology.stygian.util.Constant;
import infobite.technology.stygian.util.SessionManager;
import infobite.technology.stygian.util.Utility;
import infobite.technology.stygian.util.WebApi;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import static infobite.technology.stygian.activity.SplashActivity.mypreference;


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
    private static final int NAV_POLICY = 8;
    private static final int NAV_EXIT = 9;

    private String[] screenTitles;
    private Drawable[] screenIcons;
    ImageView cart_btn, logo_iv;
    public static TextView tooltext_tv;
    SessionManager sessionManager;
    public static TextView cart_number;
    private ImageView search_btn;
    public static int cart_count = 0;

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
        cart_count = prefs.getInt("Cart_Number", 0); //0 is the default value.
        cart_btn = findViewById(R.id.cart_btn);
        logo_iv = findViewById(R.id.iv_main_logo);
        cart_number = findViewById(R.id.cart_number);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tooltext_tv = (TextView) findViewById(R.id.tooltext);
        search_btn = (ImageView)findViewById(R.id.search_btn);
        cart_number.setText(""+cart_count);
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
            if (type.equals(Constant.CART)) {
                CartFragment cartFragment = new CartFragment(ctx, this);
                Utility.setFragment(cartFragment, ctx, Constant.CART);
            }
        } else {
            MainFragment fragment = new MainFragment(ctx);
            Utility.setFragment(fragment, ctx, Constant.HOME);
        }
    }

    @Override
    public void onItemSelected(int position) {

        if (position == NAV_ALL) {
            MainFragment fragment = new MainFragment(ctx);
            Utility.setFragment(fragment, ctx, Constant.HOME);
            slidingRootNav.closeMenu();
        } else if (position == NAV_MEN) {
            startActivity(new Intent(ctx, SubCategoryActivity.class)
                    .putExtra(Constant.ACTIVITY_ID, WebApi.ID_SUB_MEN)
                    .putExtra(Constant.ACTIVITY_TYPE, Constant.MEN));
        } else if (position == NAV_WOMEN) {
            startActivity(new Intent(ctx, SubCategoryActivity.class)
                    .putExtra(Constant.ACTIVITY_ID, WebApi.ID_SUB_WOMEN)
                    .putExtra(Constant.ACTIVITY_TYPE, Constant.WOMEN));
        } else if (position == NAV_KIDS) {
            startActivity(new Intent(ctx, ProductsActivity.class)
                    .putExtra(Constant.ACTIVITY_ID, WebApi.ID_PRODUCTS_KIDS)
                    .putExtra(Constant.ACTIVITY_TYPE, Constant.KIDS));
        } else if (position == NAV_HOME) {
            startActivity(new Intent(ctx, ProductsActivity.class)
                    .putExtra(Constant.ACTIVITY_ID, WebApi.ID_PRODUCTS_HOME)
                    .putExtra(Constant.ACTIVITY_TYPE, Constant.HOME));
        } else if (position == NAV_WISHLIST) {
            tooltext_tv.setText(Constant.WISHLIST);
            WishlistFragment fragment = new WishlistFragment(ctx);
            Utility.setFragment(fragment, ctx, Constant.WISHLIST);
            slidingRootNav.closeMenu();

        } else if (position == NAV_CART) {
            tooltext_tv.setText(Constant.CART);
            CartFragment fragment = new CartFragment(ctx, this);
            Utility.setFragment(fragment, ctx, Constant.CART);
            slidingRootNav.closeMenu();

        }  else if (position == NAV_MYACCOUNT) {
            MyProfileFragment fragment = new MyProfileFragment(ctx);
            tooltext_tv.setText(Constant.MY_PROFILE);
            Utility.setFragment(fragment, ctx, Constant.WISHLIST);
            slidingRootNav.closeMenu();

        } else if (position == NAV_POLICY) {
            tooltext_tv.setText(Constant.POLICY);
           /* CartFragment fragment = new CartFragment(ctx, this);
            Utility.setFragment(fragment, ctx, Constant.CART);*/
           Intent intent = new Intent(MainActivity.this,PolicyActivity.class);
           startActivity(intent);
            slidingRootNav.closeMenu();

        }else if (position == NAV_EXIT) {
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
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
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
                tooltext_tv.setText(Constant.CART);
                CartFragment fragment = new CartFragment(ctx, this);
                Utility.setFragment(fragment, ctx, Constant.CART);
                break;

            case R.id.iv_main_logo:
                tooltext_tv.setText(Constant.HOME);
                HomeFragment allfragment = new HomeFragment(ctx);
                Utility.setFragment(allfragment, ctx, Constant.HOME);
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if (slidingRootNav.isMenuOpened()) {
            slidingRootNav.closeMenu();
        } else {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment_byID = fm.findFragmentById(R.id.home_frame);
            String tag = fragment_byID.getTag();
            if (!tag.equals(Constant.HOME)) {
                HomeFragment homeFragment = new HomeFragment(ctx);
                Utility.setFragment(homeFragment, ctx, Constant.HOME);
            } else {
                finish();
            }
        }
    }
}


