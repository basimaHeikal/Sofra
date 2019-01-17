package com.examlpe.com.sofra.ui.fragments.restaurant;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.data.model.restaurant.MyOrders.Datum;
import com.examlpe.com.sofra.data.model.restaurant.MyOrders.RestaurantOrdersResponse;
import com.examlpe.com.sofra.R;
import com.examlpe.com.sofra.adapter.restaurant.OrdersAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class OrdersTab extends Fragment {
    //a list to store all the products
    List<Datum> ordersList;

    //the recyclerView
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String api_token;

    public String title;

    @SuppressLint("ValidFragment")
    public OrdersTab(String title) {
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_orders_tab, container, false);


        //getting the recyclerView from xml
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        //api_token = SharedPrefManagerOwner.getInstance(getActivity()).getKeyApiToken();
        api_token ="quW3tUS7GVL5lv1BfAT0Orm4CXBtmRVREu3tCP6B5WebYsVaIQYdeoyg7yay";


        //initializing the sensorList
        ordersList = new ArrayList<>();

        if(title.equals("new")){
            getRestaurantOrders("new","current");
        }else if(title.equals("current")){
            getRestaurantOrders("current","pending");

        }else if(title.equals("last")){
            getRestaurantOrders("last","completed");

        }



        return rootView;
    }


    private void getRestaurantOrders(final String title, String state) {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<RestaurantOrdersResponse> call = apiInterface.RestaurantOrders(api_token,state);
        call.enqueue(new Callback<RestaurantOrdersResponse>() {
            @Override
            public void onResponse(Call<RestaurantOrdersResponse> call, Response<RestaurantOrdersResponse> response) {
                //get response values
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    ordersList = response.body().getData().getData();
                    //creating adapter object and setting it to recyclerView
                    OrdersAdapter adapter = new OrdersAdapter(getActivity(), ordersList, title);
                    recyclerView.setAdapter(adapter);
                }else {
                    new CallToast(getActivity(),response.toString());

                }
            }

            @Override
            public void onFailure(Call<RestaurantOrdersResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                new CallToast(getActivity(),t.toString());

            }
        });
    }
}
