package com.examlpe.com.sofra.ui.fragments.general;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.examlpe.com.sofra.adapter.client.SandwichAdapter;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.helper.SetCurrentFragment;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.data.model.restaurant.FoodMenu.Datum;
import com.examlpe.com.sofra.data.model.restaurant.FoodMenu.FoodMenuResponse;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.examlpe.com.sofra.ui.activities.FoodSellActivity.TAG_LOGIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodMenuTab extends Fragment {
    //a list to store all the products
    List<Datum> itemsList;

    //the recyclerView
    RecyclerView recyclerView;

    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_food_menu_tab, container, false);


        //getting the recyclerView from xml
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        initElements(rootView);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String orderSell = SharedPrefManager.getInstance(getActivity()).getKeyOrderSell();

        if (orderSell.equals("SELL") ){
            getActivity().setTitle("قائمة الطعام");
        }

    }

    private void initElements(View rootView){


        //getting the ProgressBar from xml
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        //initializing the itemsList
        itemsList = new ArrayList<>();
        //get orderSell and foodMenu value from sharedPref to check them and take an action to make button add product visible or not
        String orderSell = SharedPrefManager.getInstance(getActivity()).getKeyOrderSell();
        //the button is gone in case of order or sell and in food menu tab
        if (orderSell.equals("ORDER")  ){
            String id = SharedPrefManager.getInstance(getActivity()).getKeyRestaurantId();
//            new CallToast(getActivity(),id);
            getItems(Integer.parseInt(id));
        }else  if (orderSell.equals("SELL") ){
            if (!SharedPrefManagerOwner.getInstance(getActivity()).isLoggedIn()) {
                //start login fragment
                SetCurrentFragment.getInstance(getActivity()).setCurrentFragment(getActivity(),TAG_LOGIN,12);
            }else {
                //getting the current api_token of the restaurant
                String api_token = SharedPrefManagerOwner.getInstance(getActivity()).getKeyApiToken();
                getOwnerItems(api_token);
//                getOwnerItems("EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv");

            }

        }


    }


    private void getItems(int id) {
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<FoodMenuResponse> call = apiInterface.getFoodMenu(id);
        call.enqueue(new Callback<FoodMenuResponse>() {
            @Override
            public void onResponse(Call<FoodMenuResponse> call, Response<FoodMenuResponse> response) {
                //get response values
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    itemsList = response.body().getData().getData();
                    //creating adapter object and setting it to recyclerView
                    SandwichAdapter adapter = new SandwichAdapter(getActivity(), itemsList, FoodMenuTab.this);
                    recyclerView.setAdapter(adapter);
                }else {

                }
            }

            @Override
            public void onFailure(Call<FoodMenuResponse> call, Throwable t) {
            }
        });
    }
    private void getOwnerItems(String api_token) {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<FoodMenuResponse> call = apiInterface.getFoodMenu(api_token);
        call.enqueue(new Callback<FoodMenuResponse>() {
            @Override
            public void onResponse(Call<FoodMenuResponse> call, Response<FoodMenuResponse> response) {
                //get response values
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    itemsList = response.body().getData().getData();
                    //creating adapter object and setting it to recyclerView
                    SandwichAdapter adapter = new SandwichAdapter(getActivity(), itemsList, FoodMenuTab.this);
                    recyclerView.setAdapter(adapter);
                }else {

                }
            }

            @Override
            public void onFailure(Call<FoodMenuResponse> call, Throwable t) {
                new CallToast(getActivity(),t.toString());
            }
        });
    }

}
