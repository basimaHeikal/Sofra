package com.examlpe.com.sofra.ui.fragments.general;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.examlpe.com.sofra.adapter.general.NewOffersAdapter;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.helper.SetCurrentFragment;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.data.model.general.OffersList.Datum;
import com.examlpe.com.sofra.data.model.general.OffersList.OffersListResponse;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.examlpe.com.sofra.ui.activities.FoodSellActivity.TAG_ADD_PRODUCT;


public class NewOffersFragment extends Fragment {
    //a list to store all the products
    List<Datum> offersList;

    //the recyclerView
    RecyclerView recyclerView;

    LinearLayout image_button_2;
    TextView image_button_2_text;
    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_new_offers, container, false);

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
            getActivity().setTitle("عروضي");
        }else if(orderSell.equals("ORDER")){
            getActivity().setTitle("جديد العروض");
        }

    }

    private void initElements(View rootView){


        image_button_2 = (LinearLayout) rootView.findViewById(R.id.image_button_2);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        image_button_2_text = (TextView) rootView.findViewById(R.id.image_button_2_text);
        String orderSell = SharedPrefManager.getInstance(getActivity()).getKeyOrderSell();
        //initializing the sensorList
        offersList = new ArrayList<>();



        if (orderSell.equals("SELL")){
            image_button_2.setVisibility(View.VISIBLE);
            image_button_2_text.setText("أضف عرض جديد");
//           getOffers("owner","EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv");
            //getting the current api_token of the restaurant
            getOffers("owner");

        }else if (orderSell.equals("ORDER")){
            getOffers("client");

        }
        image_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getActivity()).setKeyAdd("OFFER");
                SetCurrentFragment.getInstance(getActivity()).setCurrentFragment(getActivity(), TAG_ADD_PRODUCT, 11);


            }
        });
    }

    private void getOffers(String type) {
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if(type.equals("client")){
            Call<OffersListResponse> call = apiInterface.getOffersList();
            call.enqueue(new Callback<OffersListResponse>() {
                @Override
                public void onResponse(Call<OffersListResponse> call, Response<OffersListResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    //get response values
                    if(response.isSuccessful()){
                        String msg = response.body().getMsg();
                        new CallToast(getActivity(),msg);
                        offersList = response.body().getData().getData();
                        //creating adapter object and setting it to recyclerView
                        NewOffersAdapter adapter = new NewOffersAdapter(getActivity(), offersList);
                        recyclerView.setAdapter(adapter);
                    }else {

                    }
                }

                @Override
                public void onFailure(Call<OffersListResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);

                    new CallToast(getActivity(),t.toString());
                }
            });
        }else if(type.equals("owner")){
            String api_token = SharedPrefManagerOwner.getInstance(getActivity()).getKeyApiToken();
            Call<OffersListResponse> call = apiInterface.getMyOffersList(api_token);
            call.enqueue(new Callback<OffersListResponse>() {
                @Override
                public void onResponse(Call<OffersListResponse> call, Response<OffersListResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    //get response values
                    if(response.isSuccessful()){
                        String msg = response.body().getMsg();
//                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                        offersList = response.body().getData().getData();
                        //creating adapter object and setting it to recyclerView
                        NewOffersAdapter adapter = new NewOffersAdapter(getActivity(), offersList);
                        recyclerView.setAdapter(adapter);
                    }else {

                    }
                }

                @Override
                public void onFailure(Call<OffersListResponse> call, Throwable t) {
                    Log.d(TAG,"on"+t.toString());
                    new CallToast(getActivity(),t.toString());
                    progressBar.setVisibility(View.GONE);

                }
            });
        }

    }
}
