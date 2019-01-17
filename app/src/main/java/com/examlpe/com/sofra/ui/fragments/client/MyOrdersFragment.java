package com.examlpe.com.sofra.ui.fragments.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examlpe.com.sofra.helper.ViewPagerAdapter;
import com.examlpe.com.sofra.R;
import com.examlpe.com.sofra.ui.fragments.client.MyOrdersTab;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyOrdersFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_my_orders, container, false);
        unbinder = ButterKnife.bind(this, rootView);


        setupViewPager(viewpager);
        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new MyOrdersTab("current"), "طلبات حاليه");
        adapter.addFragment(new MyOrdersTab("last"), "طلبات سابقه");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewpager);
        tabLayout.getTabAt(0).select();

        viewPagerClickListener();

    }


    //addOnPageChangeListener method to change title of the action bar with the title of the tab
    private void viewPagerClickListener() {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        getActivity().setTitle("طلبات حاليه");
                        break;
                    case 1:
                        getActivity().setTitle("طلبات سابقه");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("طلبات حاليه");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
