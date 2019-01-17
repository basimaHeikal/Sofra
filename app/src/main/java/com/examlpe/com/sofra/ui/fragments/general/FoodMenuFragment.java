package com.examlpe.com.sofra.ui.fragments.general;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.helper.ViewPagerAdapter;
import com.examlpe.com.sofra.data.model.general.RestaurantDetails.Category;
import com.examlpe.com.sofra.data.model.general.RestaurantDetails.RestaurantDetailsResponse;
import com.examlpe.com.sofra.data.model.restaurant.RestaurantUser;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodMenuFragment extends Fragment {

    private ImageView imageViewRestaurant1;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView textViewRestaurantName, textViewRestaurantType, textViewState, textViewLowValue, textViewDeliveryValue;

    RatingBar ratingBar;
    //a list to store all the products
    private List<Category> foodList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate
                (R.layout.fragment_food_menu, container, false);

        //call initElements method
        initElements(rootView);

        //initializing the sensorList
        foodList = new ArrayList<>();

            //get restaurant details from SharedPrefManagerOwner in the case of owner

            //getting the orderSell key
            String orderSell = SharedPrefManager.getInstance(getActivity()).getKeyOrderSell();

            //هنعدل هنا التفاصيل بتاعت المطعم بال id مش ال shred و نشوف قائمة الطعام مش بتظهر ليه 

            if (orderSell.equals("SELL") ){
                if (!SharedPrefManagerOwner.getInstance(getActivity()).isLoggedIn()) {

                }else {
                    //getting the current user
                    RestaurantUser user = SharedPrefManagerOwner.getInstance(getActivity()).getUser();
                    String id = user.getId();
                    getRestaurantDetails(Integer.parseInt(id));
                }
            }else if(orderSell.equals("ORDER")){
                String id = SharedPrefManager.getInstance(getActivity()).getKeyRestaurantId();

                //get restaurant details by it's id in the case of client
                getRestaurantDetails(Integer.parseInt(id));
            }



        //call viewPagerClickListener method
        viewPagerClickListener();

        return rootView;
    }

    //method to set up view pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FoodMenuTab(), "قائمة الطعام");
        adapter.addFragment(new ReviewsTab(), "التعليقات و التقيم");
        adapter.addFragment(new RestaurantInfoTab(), "معلومات المتجر");
        viewPager.setAdapter(adapter);
    }


    //ViewPagerAdapter adapter

    private void initElements(View rootView) {
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        textViewRestaurantName = (TextView) rootView.findViewById(R.id.textViewRestaurantName);
        textViewRestaurantType = (TextView) rootView.findViewById(R.id.textViewRestaurantType);
        textViewState = (TextView) rootView.findViewById(R.id.textViewState);
        textViewLowValue = (TextView) rootView.findViewById(R.id.textViewLowValue);
        textViewDeliveryValue = (TextView) rootView.findViewById(R.id.textViewDeliveryValue);
        imageViewRestaurant1 =(ImageView)rootView.findViewById(R.id.imageViewRestaurant);
        ratingBar =(RatingBar) rootView.findViewById(R.id.ratingBar);


    }

    //addOnPageChangeListener method to change title of the action bar with the title of the tab
    private void viewPagerClickListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        getActivity().setTitle("قائمة الطعام");
                        break;
                    case 1:
                        getActivity().setTitle("التعليقات و التقيم");
                        break;
                    case 2:
                        getActivity().setTitle("معلومات المتجر");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void getRestaurantDetails(int id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<RestaurantDetailsResponse> call = apiInterface.getRestaurantDetails(id);
        call.enqueue(new Callback<RestaurantDetailsResponse>() {
            @Override
            public void onResponse(Call<RestaurantDetailsResponse> call, Response<RestaurantDetailsResponse> response) {
                //get response values

                if (response.isSuccessful()) {
                    StringBuilder sb = new StringBuilder();
                    foodList = response.body().getData().getCategories();
                    new CallToast(getActivity(),foodList.toString());
                    for (int i = 0; i < foodList.size(); i++){
                        sb.append(foodList.get(i).getName()) ;
                        sb.append("  ") ;
                    }

                    textViewRestaurantName.setText(response.body().getData().getName());
                    textViewRestaurantType.setText(sb);
                    textViewState.setText(response.body().getData().getAvailability());
                    textViewLowValue.setText(response.body().getData().getMinimumCharger());
                    textViewDeliveryValue.setText(response.body().getData().getDeliveryCost());
                    ratingBar.setRating(Float.parseFloat(response.body().getData().getRate()));

                    //loading the image
                    Glide.with(getActivity())
                            .load(response.body().getData().getPhotoUrl())
                            .into(imageViewRestaurant1);
                } else {

                }
            }

            @Override
            public void onFailure(Call<RestaurantDetailsResponse> call, Throwable t) {
            }
        });
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("قائمة الطعام");
    }

}
