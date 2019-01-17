package com.examlpe.com.sofra.ui.fragments.restaurant;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.examlpe.com.sofra.adapter.restaurant.OwnerProductsAdapter;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
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

import static com.examlpe.com.sofra.ui.activities.FoodSellActivity.TAG_ADD_PRODUCT;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerProductsFragment extends Fragment {
    //a list to store all the products
    List<Datum> itemsList;

    //the recyclerView
    RecyclerView recyclerView;

    LinearLayout image_button_2;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_owner_products, container, false);


        //getting the recyclerView from xml
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        initElements(rootView);

        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            getActivity().setTitle("منتجاتي");
    }

    private void initElements(View rootView){


        //initializing the itemsList
        itemsList = new ArrayList<>();
        image_button_2 = (LinearLayout) rootView.findViewById(R.id.image_button_2);

                //getting the current api_token of the restaurant
                String api_token = SharedPrefManagerOwner.getInstance(getActivity()).getKeyApiToken();
//                getOwnerItems(api_token);
                getOwnerItems("EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv");



        image_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set key Add to know that it is a product not an offer
                SharedPrefManager.getInstance(getActivity()).setKeyAdd("PRODUCT");
                SetCurrentFragment.getInstance(getActivity()).setCurrentFragment(getActivity(), TAG_ADD_PRODUCT, 11);

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
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    itemsList = response.body().getData().getData();
                    //creating adapter object and setting it to recyclerView
                    OwnerProductsAdapter adapter = new OwnerProductsAdapter(getActivity(), itemsList);
                    recyclerView.setAdapter(adapter);
                }else {

                }
            }

            @Override
            public void onFailure(Call<FoodMenuResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }


}
