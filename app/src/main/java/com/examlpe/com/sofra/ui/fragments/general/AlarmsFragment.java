package com.examlpe.com.sofra.ui.fragments.general;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.examlpe.com.sofra.adapter.general.AlarmsAdapter;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.SetCurrentFragment;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerUser;
import com.examlpe.com.sofra.data.model.general.NotificationsList.Datum;
import com.examlpe.com.sofra.data.model.general.NotificationsList.NotificationsListResponse;
import com.examlpe.com.sofra.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_LOGIN;


public class AlarmsFragment extends Fragment {

    //a list to store all the products
    List<Datum> notificationsList;

    //the recyclerView
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_alarms, container, false);

     //getting the recyclerView from xml
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        //getting the orderSell key
        String orderSell = SharedPrefManager.getInstance(getActivity()).getKeyOrderSell();

        if (orderSell.equals("ORDER") ){
            if (!SharedPrefManagerUser.getInstance(getActivity()).isLoggedIn()) {
                //start login fragment
                SetCurrentFragment.getInstance(getActivity()).setCurrentFragment(getActivity(), TAG_LOGIN, 11);

            }else {
                //getting the current api_token of the client
                String api_token = SharedPrefManagerUser.getInstance(getActivity()).getKeyApiToken();
                getNotifications(api_token,"client");
//                getNotifications("K1X6AzRlJFeVbGnHwGYsdCu0ETP1BqYC7DpMTZ3zLvKgU5feHMvsEEnKTpzh","client");
            }
        }else     if (orderSell.equals("SELL") ){
            if (!SharedPrefManagerOwner.getInstance(getActivity()).isLoggedIn()) {
                //start login fragment
                SetCurrentFragment.getInstance(getActivity()).setCurrentFragment(getActivity(), TAG_LOGIN, 12);

            }else {
                //getting the current api_token of the restaurant
                String api_token = SharedPrefManagerOwner.getInstance(getActivity()).getKeyApiToken();
                getNotifications(api_token,"owner");
//                getNotifications("EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv","owner");

            }
        }

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("التنبيهات");
    }
    private void getNotifications(String api_token,String type) {
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        if(type.equals("client")){
            Call<NotificationsListResponse> call = apiInterface.getClientNotifications(api_token);
            call.enqueue(new Callback<NotificationsListResponse>() {
                @Override
                public void onResponse(Call<NotificationsListResponse> call, Response<NotificationsListResponse> response) {
                    //get response values
                    progressBar.setVisibility(View.GONE);
                    if(response.isSuccessful()){
                        String msg = response.body().getMsg();
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                        notificationsList = response.body().getData().getData();
                        //creating adapter object and setting it to recyclerView
                        AlarmsAdapter adapter = new  AlarmsAdapter(getActivity(), notificationsList);
                        recyclerView.setAdapter(adapter);
                    }else {

                    }
                }

                @Override
                public void onFailure(Call<NotificationsListResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }else if(type.equals("owner")){
            Call<NotificationsListResponse> call = apiInterface.getRestaurantNotifications(api_token);
            call.enqueue(new Callback<NotificationsListResponse>() {
                @Override
                public void onResponse(Call<NotificationsListResponse> call, Response<NotificationsListResponse> response) {
                    //get response values
                    progressBar.setVisibility(View.GONE);

                    if(response.isSuccessful()){
                        String msg = response.body().getMsg();
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                        notificationsList = response.body().getData().getData();
                        //creating adapter object and setting it to recyclerView
                        AlarmsAdapter adapter = new  AlarmsAdapter(getActivity(), notificationsList);
                        recyclerView.setAdapter(adapter);
                    }else {

                    }
                }

                @Override
                public void onFailure(Call<NotificationsListResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }


    }

}
