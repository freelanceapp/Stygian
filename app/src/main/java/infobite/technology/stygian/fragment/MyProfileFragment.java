package infobite.technology.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import infobite.technology.stygian.R;
import infobite.technology.stygian.util.Constant;
import infobite.technology.stygian.util.Utility;

import static infobite.technology.stygian.activity.MainActivity.tooltext_tv;


@SuppressLint("ValidFragment")
public class MyProfileFragment extends Fragment implements View.OnClickListener {

    Context ctx;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    View profile_view, order_view, address_view;
    TextView profile_tv, order_tv, address_tv;
    FrameLayout frameLayout;

    @SuppressLint("ValidFragment")
    public MyProfileFragment(Context ctx) {
        this.ctx = ctx;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);
        tooltext_tv.setText(Constant.MY_ACCOUNT);
        initXml(view);
        viewPager = view.findViewById(R.id.viewpager1);
        tabLayout = view.findViewById(R.id.tabs1);
//        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    private void initXml(View view) {
        profile_tv = view.findViewById(R.id.tv_tab_profile);
        order_tv = view.findViewById(R.id.tv_tab_orders);
        address_tv = view.findViewById(R.id.tv_tab_address);
        frameLayout = view.findViewById(R.id.fl_myprofile);
        profile_view = view.findViewById(R.id.view_profile_tab);
        order_view = view.findViewById(R.id.view_order_tab);
        address_view = view.findViewById(R.id.view_address_tab);

        profile_tv.setOnClickListener(this);
        order_tv.setOnClickListener(this);
        address_tv.setOnClickListener(this);

        ProfileFragment fragment = new ProfileFragment(ctx);
        Utility.setProfileFragment(fragment, ctx);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new ProfileFragment(ctx), "Profile");
        adapter.addFragment(new MyOrderFragment(ctx), "My Orders");
        adapter.addFragment(new MyAddressFragment(ctx), "My Address");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_tab_profile:
                setTabview(true, false, false);
                ProfileFragment fragment = new ProfileFragment(ctx);
                Utility.setProfileFragment(fragment, ctx);
                break;

            case R.id.tv_tab_orders:
                setTabview(false, true, false);
                MyOrderFragment fragment1 = new MyOrderFragment(ctx);
                Utility.setProfileFragment(fragment1, ctx);
                break;

            case R.id.tv_tab_address:
                setTabview(false, false, true);
                MyAddressFragment fragment2 = new MyAddressFragment(ctx);
                Utility.setProfileFragment(fragment2, ctx);
                break;
        }
    }

    private void setTabview(boolean profile, boolean orders, boolean address) {

        if (profile) {
            profile_view.setVisibility(View.VISIBLE);
        } else {
            profile_view.setVisibility(View.GONE);
        }
        if (orders) {
            order_view.setVisibility(View.VISIBLE);
        } else {
            order_view.setVisibility(View.GONE);
        }
        if (address) {
            address_view.setVisibility(View.VISIBLE);
        } else {
            address_view.setVisibility(View.GONE);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}