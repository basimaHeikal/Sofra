package com.examlpe.com.sofra.ui.fragments.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.examlpe.com.sofra.adapter.client.MyOrdersAdapter;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.data.model.client.MyOrders.Datum;
import com.examlpe.com.sofra.data.model.client.MyOrders.MyOrdersResponse;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class MyOrdersTab extends Fragment {

    //a list to store all the products
    List<Datum> ordersList;


    String api_token;

    public String title;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;

    @SuppressLint("ValidFragment")
    public MyOrdersTab(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_my_orders_tab, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        initElements();

        return rootView;
    }

    private void initElements(){
        //getting the recyclerView from xml
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // api_token = SharedPrefManagerUser.getInstance(getActivity()).getKeyApiToken();
        api_token = "HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB";

        //initializing the sensorList
        ordersList = new ArrayList<>();

        if (title.equals("current")) {
            getClientOrders("current", "current");
        } else if (title.equals("last")) {
            getClientOrders("last", "completed");

        }

    }

    private void getClientOrders(final String title, String state) {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<MyOrdersResponse> call = apiInterface.ClientOrders(api_token, state);
        call.enqueue(new Callback<MyOrdersResponse>() {
            @Override
            public void onResponse(Call<MyOrdersResponse> call, Response<MyOrdersResponse> response) {
                //get response values
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    ordersList = response.body().getData().getData();
                    //creating adapter object and setting it to recyclerView
                    MyOrdersAdapter adapter = new MyOrdersAdapter(getActivity(), ordersList, title);
                    recyclerView.setAdapter(adapter);

                } else {
                    new CallToast(getActivity(), response.toString());

                }
            }

            @Override
            public void onFailure(Call<MyOrdersResponse> call, Throwable t) {
                new CallToast(getActivity(), t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
