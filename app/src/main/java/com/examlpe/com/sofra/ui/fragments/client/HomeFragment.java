package com.examlpe.com.sofra.ui.fragments.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.examlpe.com.sofra.adapter.client.RestaurantAdapter;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.data.model.general.RestaurantList.Datum;
import com.examlpe.com.sofra.data.model.general.RestaurantList.RestaurantListResponse;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    //a list to store all the products
    List<Datum> restaurantList;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the sensorList
        restaurantList = new ArrayList<>();

        //call getRestaurants method to put restaurants in recyclerView
        getRestaurants();
        return rootView;
    }



    private void getRestaurants() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<RestaurantListResponse> call = apiInterface.getRestaurantList();
        call.enqueue(new Callback<RestaurantListResponse>() {
            @Override
            public void onResponse(Call<RestaurantListResponse> call, Response<RestaurantListResponse> response) {
                //get response values
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    restaurantList = response.body().getData().getData();
                    //creating adapter object and setting it to recyclerView
                    RestaurantAdapter adapter = new RestaurantAdapter(getActivity(), restaurantList, HomeFragment.this);
                    recyclerView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<RestaurantListResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("طلب الطعام");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

