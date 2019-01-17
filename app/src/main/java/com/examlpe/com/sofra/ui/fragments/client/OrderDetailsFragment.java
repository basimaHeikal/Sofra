package com.examlpe.com.sofra.ui.fragments.client;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.examlpe.com.sofra.data.local.room.ItemFoodData;
import com.examlpe.com.sofra.data.local.room.RoomDao;
import com.examlpe.com.sofra.data.local.room.RoomManger;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerUser;
import com.examlpe.com.sofra.data.model.client.NewOrder.NewOrderResponse;
import com.examlpe.com.sofra.data.model.general.paymentMethods.Datum;
import com.examlpe.com.sofra.data.model.general.paymentMethods.PaymentMethodsResponse;
import com.examlpe.com.sofra.R;
import com.examlpe.com.sofra.adapter.client.PaymentMethodsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailsFragment extends Fragment {


    //a list to store all the products
    List<ItemFoodData> foodItems;

    List<String> quantity;
    List<Integer> id;
    List<String> note;

    //a list to store all the products
    List<Datum>restaurantList;

    String restaurant_id, address;

    EditText editTextMessage;
    TextView textViewAddress,textViewSumValue,textViewDeliveryCostValue,textViewTotalValue,textViewId;

    RecyclerView recyclerView;

    Button buttonBuy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate
                (R.layout.fragment_order_details, container, false);


        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //initializing the sensorList
        restaurantList = new ArrayList<>();
        getPaymentMethods();


        editTextMessage=(EditText)rootView.findViewById(R.id.editTextMessage);
        textViewAddress=(TextView) rootView.findViewById(R.id.textViewAddress);
        textViewSumValue=(TextView) rootView.findViewById(R.id.textViewSumValue);
        textViewDeliveryCostValue=(TextView) rootView.findViewById(R.id.textViewDeliveryCostValue);
        textViewTotalValue=(TextView) rootView.findViewById(R.id.textViewTotalValue);
        textViewId=(TextView) rootView.findViewById(R.id.textViewId);

//        String sum = SharedPrefManager.getInstance(getActivity()).getKeyTotal();
//        textViewSumValue.setText(sum);

        address = SharedPrefManagerUser.getInstance(getActivity()).getUser().getAddress();
        textViewAddress.setText(address);



        restaurant_id = SharedPrefManager.getInstance(getActivity()).getKeyRestaurantId();
//        new CallToast(getActivity(),restaurant_id);

        RoomManger roomManger = RoomManger.getInstance(getActivity());
        final RoomDao roomDao = roomManger.roomDao();

        quantity  = new ArrayList<>();
        id  = new ArrayList<>();
        note  = new ArrayList<>();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                foodItems = new ArrayList<>();

                foodItems = roomDao.getAllItem();

                for (int i = 0; i < foodItems.size(); i++){
                    String q = foodItems.get(i).getQuantity();
                    quantity.add(q);
                    String n = foodItems.get(i).getItem_note();
                    note.add(n);
                    Integer ii = foodItems.get(i).getId();
                    id.add(ii);
                }
            }
        });

        buttonBuy=(Button)rootView.findViewById(R.id.buttonBuy);
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeNewOrder();
            }
        });



        return rootView;
    }

    public void MakeNewOrder() {
        String OrderNote = editTextMessage.getText().toString();
        String phone = SharedPrefManagerUser.getInstance(getActivity()).getUser().getPhone();
        String name = SharedPrefManagerUser.getInstance(getActivity()).getUser().getName();
        String api_token = SharedPrefManagerUser.getInstance(getActivity()).getKeyApiToken();

        String paymentMethodId = textViewId.getText().toString();


//        new CallToast(getActivity(),id.toString()+quantity.toString()+note.toString()+phone+name+api_token+OrderNote);
        new CallToast(getActivity(),paymentMethodId);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Ordering...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<NewOrderResponse> call = apiInterface.NewOrder(id, quantity,note,
                restaurant_id,OrderNote,address,paymentMethodId,
                phone,name,api_token);
        call.enqueue(new Callback<NewOrderResponse>() {
            @Override
            public void onResponse(Call<NewOrderResponse> call, Response<NewOrderResponse> response) {

                pDialog.hide();
                if (response.isSuccessful()) {

                    //get response values
                    String msg = response.body().getMsg();
                    new CallToast(getActivity(),msg);
                    if (msg.equals("تم الطلب بنجاح")){

                        RoomManger roomManger = RoomManger.getInstance(getActivity());
                        final RoomDao roomDao = roomManger.roomDao();

                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                roomDao.deleteAllItemToCar();
                            }
                        });
                    }

                } else {
                    new CallToast(getActivity(),"failed");

                }
            }
            @Override
            public void onFailure(Call<NewOrderResponse> call, Throwable t) {
                pDialog.hide();
                new CallToast(getActivity(),t.toString());
            }
        });
    }

    private void getPaymentMethods() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<PaymentMethodsResponse> call = apiInterface.getPaymentMethodsList();
        call.enqueue(new Callback<PaymentMethodsResponse>() {
            @Override
            public void onResponse(Call<PaymentMethodsResponse> call, Response<PaymentMethodsResponse> response) {
                //get response values
                if(response.isSuccessful()){
                    restaurantList = response.body().getData();
                    //creating adapter object and setting it to recyclerView
                    PaymentMethodsAdapter adapter = new PaymentMethodsAdapter(getActivity(), restaurantList,textViewId);
                    recyclerView.setAdapter(adapter);
                }else {

                }
            }

            @Override
            public void onFailure(Call<PaymentMethodsResponse> call, Throwable t) {
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("تفاصيل الطلب");
    }
}
