package com.examlpe.com.sofra.ui.fragments.general;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.examlpe.com.sofra.adapter.general.ReviewsAdapter;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerUser;
import com.examlpe.com.sofra.data.model.general.reviews.Datum;
import com.examlpe.com.sofra.data.model.general.reviews.ReviewsResponse;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewsTab extends Fragment {
    //a list to store all the reviews
    List<Datum> reviewList;

    //the recyclerView
    RecyclerView recyclerView;

    ProgressBar progressBar;
    CardView cardView1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_reviews_tab, container, false);

        //getting the recyclerView from xml
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //call setRecyclerView method
        setRecyclerView(rootView);

        return rootView;
    }

    private void setRecyclerView(View rootView){
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        cardView1=(CardView)rootView.findViewById(R.id.cardView1);

        //initializing the reviewList
        reviewList = new ArrayList<>();

        //getting orderSell value
        String orderSell = SharedPrefManager.getInstance(getActivity()).getKeyOrderSell();

        if (orderSell.equals("ORDER")) {
            if (!SharedPrefManagerUser.getInstance(getActivity()).isLoggedIn()) {

            }else {
                String api_token = SharedPrefManagerUser.getInstance(getActivity()).getKeyApiToken();
                String restaurant_id = SharedPrefManager.getInstance(getActivity()).getKeyRestaurantId();

                getReviews(api_token,restaurant_id);
            }
        }else if (orderSell.equals("SELL")) {
            if (!SharedPrefManagerOwner.getInstance(getActivity()).isLoggedIn()) {

            }else {
                cardView1.setVisibility(View.GONE);
                String api_token = SharedPrefManagerOwner.getInstance(getActivity()).getKeyApiToken();
                String restaurant_id = SharedPrefManagerOwner.getInstance(getActivity()).getUser().getId();
                getReviews(api_token, String.valueOf(restaurant_id));
            }
        }







    }

 private void getReviews(String api_token,String restaurant_id) {

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ReviewsResponse> call = apiInterface.getReviewsList(api_token,restaurant_id,"1");
        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                //get response values
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    reviewList = response.body().getData().getData();
                    //creating adapter object and setting it to recyclerView
                    ReviewsAdapter adapter = new ReviewsAdapter(getActivity(), reviewList);
                    recyclerView.setAdapter(adapter);
                }else {

                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                new CallToast(getActivity(),t.toString());
            }
        });
    }


}
