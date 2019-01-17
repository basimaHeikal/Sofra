package com.examlpe.com.sofra.adapter.restaurant;


import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.data.model.restaurant.AcceptOrder.AcceptOrderResponse;
import com.examlpe.com.sofra.data.model.restaurant.ConfirmOrder.ConfirmOrderResponse;
import com.examlpe.com.sofra.data.model.restaurant.MyOrders.Datum;
import com.examlpe.com.sofra.data.model.restaurant.MyOrders.Item;
import com.examlpe.com.sofra.data.model.restaurant.RejectOrder.RejectOrderResponse;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.SensorViewHolder>{


    private  String title;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Datum> SensorList;

    private List<Item> foodList;
    String api_token,order_id;


    //getting the context and product list with constructor
    public OrdersAdapter(Context mCtx, List<Datum> SensorList, String title) {
        this.mCtx = mCtx;
        this.SensorList =SensorList;
        this.title=title;
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        //This method returns a new instance of our ViewHolder.
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_fragment_owner_orders_tab, null);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SensorViewHolder holder, final int position) {

        api_token = SharedPrefManagerOwner.getInstance(mCtx).getKeyApiToken();

        //This method binds the data to the view holder.
        //getting the product of the specified position
        final Datum restaurant = SensorList.get(position);


        // holder.currentItem = items.get(position);
        //binding the data with the view holder views
        holder.textViewClientNameValue.setText(restaurant.getClient().getName());
        holder.textViewPriceValue.setText(restaurant.getCost());
        holder.textViewDeliveryCostValue.setText(restaurant.getDeliveryCost());
        holder.textViewOrderNumberValue.setText(String.valueOf(restaurant.getId()));
        holder.textViewTotalValue.setText(restaurant.getTotal());
        holder.textViewOrderAddressValue.setText(restaurant.getAddress());
        holder.rPhone.setText(restaurant.getClient().getPhone());
        holder.textViewPhone.setText(restaurant.getClient().getPhone());
        order_id = holder.textViewOrderNumberValue.getText().toString();

        if(title.equals("new")){
            holder.currentOrdersLinear.setVisibility(View.GONE);
            holder.lastOrderLinear.setVisibility(View.GONE);

        }else if(title.equals("current")){
            holder.newOrderLinear.setVisibility(View.GONE);
            holder.lastOrderLinear.setVisibility(View.GONE);

        }else if(title.equals("last")){
            holder.newOrderLinear.setVisibility(View.GONE);
            holder.currentOrdersLinear.setVisibility(View.GONE);

        }

        foodList = new ArrayList<>();

        foodList = restaurant.getItems();
       // String photo =foodList.get(0).getPhotoUrl().toString();

//        //loading the image
//       Glide.with(mCtx)
//                .load(photo)
//                .into(holder.imageViewRestaurant);

       //call client
       holder.image_button_1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               makeCall(restaurant);
           }
       });

        holder.linearCallClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall(restaurant);

            }
        });
        //accept
        holder.image_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOrders(api_token,order_id,"accept");
            }
        });
        //reject
        holder.image_button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOrders(api_token,order_id,"reject");

            }
        });
        //confirm order
        holder.linearConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOrders(api_token,order_id,"confirm");

            }
        });

    }


    @Override
    public int getItemCount() {
        //This returns the size of the List.
        return SensorList.size();
    }

   /* @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.image_button_1:
                // do your code
                break;

            case R.id.linearCallClient:
                // do your code
                break;

            case R.id.image_button_2:
                // do your code
                break;

            case R.id.image_button_3:
                // do your code
                break;

            case R.id.linearConfirm:
                // do your code
                break;

            default:
                break;
        }
    }*/

    /*represents the views of our RecyclerView*/
    class SensorViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ClipData.Item currentItem;
        TextView textViewClientNameValue,textViewPriceValue,textViewDeliveryCostValue,textViewOrderNumberValue,
                textViewTotalValue,textViewOrderAddressValue,textViewPhone,rPhone;
        ImageView imageViewRestaurant;
        LinearLayout newOrderLinear,currentOrdersLinear,lastOrderLinear,image_button_2,image_button_3,linearConfirm,image_button_1,linearCallClient;
        public SensorViewHolder(View itemView) {
            super(itemView);
            textViewClientNameValue = itemView.findViewById(R.id.textViewClientNameValue);
            textViewDeliveryCostValue = itemView.findViewById(R.id.textViewDeliveryCostValue);
            textViewTotalValue = itemView.findViewById(R.id.textViewTotalValue);
            textViewPriceValue = itemView.findViewById(R.id.textViewPriceValue);
            textViewOrderNumberValue=itemView.findViewById(R.id.textViewOrderNumberValue);
            textViewOrderAddressValue=itemView.findViewById(R.id.textViewOrderAddressValue);
            newOrderLinear=itemView.findViewById(R.id.newOrderLinear);
            currentOrdersLinear=itemView.findViewById(R.id.currentOrdersLinear);
            lastOrderLinear=itemView.findViewById(R.id.lastOrderLinear);
            //accept
            image_button_2=itemView.findViewById(R.id.image_button_2);
            //reject
            image_button_3=itemView.findViewById(R.id.image_button_3);
            //call client
            image_button_1=itemView.findViewById(R.id.image_button_1);
            linearCallClient=itemView.findViewById(R.id.linearCallClient);

            //confirm
            linearConfirm=itemView.findViewById(R.id.linearConfirm);
            rPhone=itemView.findViewById(R.id.rPhone);
            textViewPhone=itemView.findViewById(R.id.rPhone);

            imageViewRestaurant = itemView.findViewById(R.id.imageViewRestaurant);
            view = itemView;



        }
    }

    private void handleOrders(final String api_token, String order_id,  String type) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (type.equals("accept")){
            Call<AcceptOrderResponse> call = apiInterface.AcceptOrder(api_token,order_id);
            call.enqueue(new Callback<AcceptOrderResponse>() {
                @Override
                public void onResponse(Call<AcceptOrderResponse> call, Response<AcceptOrderResponse> response) {
                    //get response values
                    if(response.isSuccessful()){
                        String msg = response.body().getMsg();
                        new CallToast(mCtx,msg);
                    }else {

                    }
                }

                @Override
                public void onFailure(Call<AcceptOrderResponse> call, Throwable t) {

                }
            });
        }else  if (type.equals("reject")){
            Call<RejectOrderResponse> call = apiInterface.RejectOrder(api_token,order_id);
            call.enqueue(new Callback<RejectOrderResponse>() {
                @Override
                public void onResponse(Call<RejectOrderResponse> call, Response<RejectOrderResponse> response) {
                    //get response values
                    if(response.isSuccessful()){
                        String msg = response.body().getMsg();
                        new CallToast(mCtx,msg);
                    }else {

                    }
                }

                @Override
                public void onFailure(Call<RejectOrderResponse> call, Throwable t) {

                }
            });
        }else  if (type.equals("confirm")){
            Call<ConfirmOrderResponse> call = apiInterface.ConfirmOrder(api_token,order_id);
            call.enqueue(new Callback<ConfirmOrderResponse>() {
                @Override
                public void onResponse(Call<ConfirmOrderResponse> call, Response<ConfirmOrderResponse> response) {
                    //get response values
                    if(response.isSuccessful()){
                        String msg = response.body().getMsg();
                        new CallToast(mCtx,msg);
                    }else {

                    }
                }

                @Override
                public void onFailure(Call<ConfirmOrderResponse> call, Throwable t) {

                }
            });
        }

    }

    private void makeCall(Datum restaurant){
        //make a call to his client number
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+String.valueOf(restaurant.getClient().getPhone())));
        //check call permission
        if (ActivityCompat.checkSelfPermission(mCtx,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mCtx.startActivity(callIntent);
    }


}