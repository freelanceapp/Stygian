package infobite.technology.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import infobite.technology.stygian.activity.MainActivity;
import infobite.technology.stygian.R;
import infobite.technology.stygian.util.ConstantData;


@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {

    Context ctx;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @SuppressLint("ValidFragment")
    public MainFragment(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        MainActivity.tooltext_tv.setText("Home");
        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(ctx), "All");
        adapter.addFragment(new MensFragment(ctx, ConstantData.MEN), "MEN");
        adapter.addFragment(new WomensFragment(ctx, ConstantData.WOMEN), "WOMEN");
        adapter.addFragment(new KidsFragment(ctx, ConstantData.KIDS), "KIDS");
        adapter.addFragment(new MobileFragment(ctx, ConstantData.MOBILE), "MOBILES");
        viewPager.setAdapter(adapter);
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