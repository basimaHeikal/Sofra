package com.examlpe.com.sofra.ui.fragments.restaurant;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.data.model.restaurant.Commissions.CommissionsResponse;
import com.examlpe.com.sofra.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TipsFragment extends Fragment {


    TextView textView,textViewTotal,textViewCommissions,textViewPayments,textViewNetCommissions;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_tips, container, false);

        textView=(TextView)rootView.findViewById(R.id.textView);
        textViewTotal=(TextView)rootView.findViewById(R.id.textViewTotal);
        textViewCommissions=(TextView)rootView.findViewById(R.id.textViewCommissions);
        textViewPayments=(TextView)rootView.findViewById(R.id.textViewPayments);
        textViewNetCommissions=(TextView)rootView.findViewById(R.id.textViewNetCommissions);

        getCommissions();

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("العموله");
    }

    private void getCommissions() {
        String api_token= SharedPrefManagerOwner.getInstance(getActivity()).getKeyApiToken();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommissionsResponse> call = apiInterface.Commissions(api_token);
        call.enqueue(new Callback<CommissionsResponse>() {
            @Override
            public void onResponse(Call<CommissionsResponse> call, Response<CommissionsResponse> response) {
                //get response values
                if (response.isSuccessful()) {
                    textView.setText("يرجى تحويل" +" "+response.body().getData().getCommission().toString()+" " + "من الربح المباع في مده أقصاها شهر علما بأن جميع الطلبات ستكون مسجله على حسابك الشخصي");
                    textViewTotal.setText(response.body().getData().getTotal().toString());
                    textViewCommissions.setText(response.body().getData().getCommissions().toString());
                    textViewPayments.setText(response.body().getData().getPayments().toString());
                    textViewNetCommissions.setText(response.body().getData().getNetCommissions().toString());

                } else {

                    new CallToast(getActivity(),response.toString());

                }
            }

            @Override
            public void onFailure(Call<CommissionsResponse> call, Throwable t) {
                new CallToast(getActivity(),t.toString());
            }
        });
    }



}
