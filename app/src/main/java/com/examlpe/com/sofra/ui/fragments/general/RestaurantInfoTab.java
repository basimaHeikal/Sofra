package com.examlpe.com.sofra.ui.fragments.general;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.data.model.general.City;
import com.examlpe.com.sofra.data.model.general.Region;
import com.examlpe.com.sofra.data.model.general.RestaurantDetails.RestaurantDetailsResponse;
import com.examlpe.com.sofra.data.model.restaurant.ChangeState.ChangeStateResponse;
import com.examlpe.com.sofra.data.model.restaurant.RestaurantUser;
import com.examlpe.com.sofra.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantInfoTab extends Fragment {


    Button buttonEditRestaurant;
    SwitchCompat switch1;
    EditText textViewCityValue,textViewRegionValue,textViewMinimumChargerValue,textViewDeliveryCostValue;
    TextView textViewAvailabilityValue;
    String id;
    String orderSell;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_restaurant_info_tab, container, false);

        InitElements(rootView);

        return rootView;
    }


    @SuppressLint("ResourceAsColor")
    private void InitElements(View rootView) {


        buttonEditRestaurant = (Button) rootView.findViewById(R.id.buttonEditRestaurant);
        switch1 = (SwitchCompat) rootView.findViewById(R.id.switch1);
        textViewAvailabilityValue = (TextView) rootView.findViewById(R.id.textViewAvailabilityValue);
        textViewCityValue = (EditText) rootView.findViewById(R.id.textViewCityValue);
        textViewRegionValue = (EditText) rootView.findViewById(R.id.textViewRegionValue);
        textViewMinimumChargerValue = (EditText) rootView.findViewById(R.id.textViewMinimumChargerValue);
        textViewDeliveryCostValue = (EditText) rootView.findViewById(R.id.textViewDeliveryCostValue);

        //getting orderSell value
        orderSell = SharedPrefManager.getInstance(getActivity()).getKeyOrderSell();

        if (orderSell.equals("SELL")) {
            if (!SharedPrefManagerOwner.getInstance(getActivity()).isLoggedIn()) {

            }else {
                //getting the current user
                RestaurantUser user = SharedPrefManagerOwner.getInstance(getActivity()).getUser();
                //getting the current Region
                Region region =SharedPrefManagerOwner.getInstance(getActivity()).getRegion();
                //getting the current city
                City city =SharedPrefManagerOwner.getInstance(getActivity()).getKeyCityName();
                textViewAvailabilityValue.setText(user.getAvailability());
                textViewCityValue.setText(city.getName());
                textViewRegionValue.setText(region.getName());
                textViewMinimumChargerValue.setText(user.getMinimumCharger());
                textViewDeliveryCostValue.setText(user.getDeliveryCost());
                if(textViewAvailabilityValue.getText().toString().equals("open")){
                    switch1.setChecked(true);
                    textViewAvailabilityValue.setTextColor(R.color.colorPowerOn);
                }
                else if(textViewAvailabilityValue.getText().toString().equals("closed")) {
                    switch1.setChecked(false);
                    textViewAvailabilityValue.setTextColor(R.color.colorPowerOff);
                }

            }





        } else if (orderSell.equals("ORDER")) {
            buttonEditRestaurant.setVisibility(View.GONE);
            textViewCityValue.setEnabled(false);
            textViewRegionValue.setEnabled(false);
            textViewMinimumChargerValue.setEnabled(false);
            textViewDeliveryCostValue.setEnabled(false);

            switch1.setVisibility(View.INVISIBLE);
            id = SharedPrefManager.getInstance(getActivity()).getKeyRestaurantId();
            //get restaurant details by it's id in the case of client
            getRestaurantDetails(Integer.parseInt(id));

        }

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String check = String.valueOf(isChecked);
               if(check.equals("true")){
                   ChangeState("open");
                   textViewAvailabilityValue.setTextColor(R.color.colorPowerOn);
               }
               else if(check.equals("false")) {
                   ChangeState("closed");
                   textViewAvailabilityValue.setTextColor(R.color.colorPowerOff);

               }

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
                    textViewCityValue.setText(response.body().getData().getRegion().getCity().getName());
                    textViewRegionValue.setText(response.body().getData().getRegion().getName());
                    textViewAvailabilityValue.setText(response.body().getData().getAvailability());
                    textViewMinimumChargerValue.setText(response.body().getData().getMinimumCharger());
                    textViewDeliveryCostValue.setText(response.body().getData().getDeliveryCost());

                } else {

                }
            }

            @Override
            public void onFailure(Call<RestaurantDetailsResponse> call, Throwable t) {
            }
        });
    }


    public void ChangeState(String state) {

        String api_token = SharedPrefManagerOwner.getInstance(getActivity()).getKeyApiToken();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ChangeStateResponse> call = apiInterface.ChangeState(api_token,state);
        call.enqueue(new Callback<ChangeStateResponse>() {
            @Override
            public void onResponse(Call<ChangeStateResponse> call, Response<ChangeStateResponse> response) {
                if (response.isSuccessful()) {
                     RestaurantUser user = new RestaurantUser(
                            String.valueOf(response.body().getData().getId()),
                            response.body().getData().getName(),
                            response.body().getData().getEmail(),
                            response.body().getData().getDeliveryCost(),
                            response.body().getData().getMinimumCharger(),
                            response.body().getData().getPhone(),
                            response.body().getData().getPhoto(),
                            response.body().getData().getAvailability(),
                           String.valueOf( response.body().getData().getRate()));

                    //storing the user in shared preferences
                    SharedPrefManagerOwner.getInstance(getActivity()).setUser(user);
                    textViewAvailabilityValue.setText(response.body().getData().getAvailability());



                } else {
                    new CallToast(getActivity(),"failed");

                }
            }
            @Override
            public void onFailure(Call<ChangeStateResponse> call, Throwable t) {

            }
        });
    }


}
