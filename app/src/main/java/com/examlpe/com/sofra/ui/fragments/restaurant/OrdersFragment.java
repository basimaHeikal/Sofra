package com.examlpe.com.sofra.ui.fragments.restaurant;


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


public class OrdersFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_orders, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPagerClickListener();
        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OrdersTab("new"), "طلبات جديده");
        adapter.addFragment(new OrdersTab("current"), "طلبات حاليه");
        adapter.addFragment(new OrdersTab("last"), "طلبات سابقه");
        viewPager.setAdapter(adapter);
    }
    //addOnPageChangeListener method to change title of the action bar with the title of the tab
    private void viewPagerClickListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        getActivity().setTitle("طلبات جديده");
                        break;
                    case 1:
                        getActivity().setTitle("طلبات حاليه");
                        break;
                    case 2:
                        getActivity().setTitle("طلبات سابقه");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("طلبات جديده");
    }

}
