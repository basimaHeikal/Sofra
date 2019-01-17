package com.examlpe.com.sofra.ui.fragments.general;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;

import com.examlpe.com.sofra.helper.ViewPagerAdapter;
import com.examlpe.com.sofra.R;
import com.examlpe.com.sofra.ui.fragments.general.ContactUsTab;


public class ContactUsFragment extends Fragment  {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_contact_us, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPagerClickListener();


        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ContactUsTab("complaint"), "شكوى");
        adapter.addFragment(new ContactUsTab("suggestion"), "اقتراح");
        adapter.addFragment(new ContactUsTab("inquiry"), "استعلام");
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
                        getActivity().setTitle("شكوى");
                        break;
                    case 1:
                        getActivity().setTitle("اقتراح");
                        break;
                    case 2:
                        getActivity().setTitle("استعلام");
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
        getActivity().setTitle("شكوى");
    }


}
