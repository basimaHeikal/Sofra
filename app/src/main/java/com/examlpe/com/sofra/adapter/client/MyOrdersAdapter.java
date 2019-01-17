package com.examlpe.com.sofra.adapter.client;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerUser;
import com.examlpe.com.sofra.data.model.client.ConfirmOrder.ConfirmClientOrderResponse;
import com.examlpe.com.sofra.data.model.client.DeclineOrder.DeclineOrderResponse;
import com.examlpe.com.sofra.data.model.client.MyOrders.Datum;
import com.examlpe.com.sofra.data.model.client.MyOrders.Item;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.SensorViewHolder> {


    private  String title;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Datum> SensorList;
    private List<Item> foodList;


    String api_token,order_id;


    //getting the context and product list with constructor
    public MyOrdersAdapter(Context mCtx, List<Datum> SensorList, String title) {
        this.mCtx = mCtx;
        this.SensorList =SensorList;
        this.title=title;
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        //This method returns a new instance of our ViewHolder.
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_fragment_my_orders, null);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SensorViewHolder holder, final int position) {

        api_token = SharedPrefManagerUser.getInstance(mCtx).getKeyApiToken();
        //This method binds the data to the view holder.
        //getting the product of the specified position
        final Datum restaurant = SensorList.get(position);
        // holder.currentItem = items.get(position);
        //binding the data with the view holder views
        holder.textViewRestaurantName.setText(restaurant.getRestaurant().getName());
        holder.textViewTotalValue.setText(restaurant.getTotal());
        holder.textViewDeliveryCostValue.setText(restaurant.getDeliveryCost());
        holder.textViewPriceValue.setText(restaurant.getCost());
        holder.textViewOrderNumberValue.setText(String.valueOf(restaurant.getId()));

        foodList = new ArrayList<>();

        foodList = restaurant.getItems();

       String photo =restaurant.getRestaurant().getPhotoUrl().toString();
        //loading the image
        Glide.with(mCtx)
                .load(photo)
                .into(holder.imageViewRestaurant);


        if(title.equals("current")){
            holder.image_button_2.setVisibility(View.GONE);

        }else if(title.equals("last")){

        }


        holder.linearConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOrders(api_token,order_id,"confirm");
            }
        });

        holder.linearReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOrders(api_token,order_id,"decline");

            }
        });


    }


    @Override
    public int getItemCount() {
        //This returns the size of the List.
        return SensorList.size();
    }

    /*represents the views of our RecyclerView*/
    class SensorViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ClipData.Item currentItem;
        TextView textViewRestaurantName,textViewPriceValue,textViewDeliveryCostValue,textViewTotalValue,textViewOrderNumberValue;
        ImageView imageViewRestaurant;
        LinearLayout image_button_2,linearReject,linearConfirm;


        public SensorViewHolder(View itemView) {
            super(itemView);
            textViewRestaurantName = itemView.findViewById(R.id.textViewRestaurantName);
            textViewDeliveryCostValue = itemView.findViewById(R.id.textViewDeliveryCostValue);
            textViewTotalValue = itemView.findViewById(R.id.textViewTotalValue);
            textViewPriceValue = itemView.findViewById(R.id.textViewPriceValue);
            textViewOrderNumberValue=itemView.findViewById(R.id.textViewOrderNumberValue);
            imageViewRestaurant = itemView.findViewById(R.id.imageViewRestaurant);
            image_button_2 = itemView.findViewById(R.id.image_button_2);
            linearReject = itemView.findViewById(R.id.linearReject);
            linearConfirm = itemView.findViewById(R.id.linearConfirm);

            view = itemView;

        }
    }

    private void handleOrders(final String api_token, String order_id,  String type) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        if (type.equals("confirm")){
            final ProgressDialog pDialog = new ProgressDialog(mCtx);
            pDialog.setMessage("Confirming...");
            pDialog.show();

            Call<ConfirmClientOrderResponse> call = apiInterface.ConfirmClientOrder(api_token,order_id);
            call.enqueue(new Callback<ConfirmClientOrderResponse>() {
                @Override
                public void onResponse(Call<ConfirmClientOrderResponse> call, Response<ConfirmClientOrderResponse> response) {
                    pDialog.hide();
                    //get response values
                    if(response.isSuccessful()){
                        String msg = response.body().getMsg();
                        new CallToast(mCtx,msg);
                    }else {

                    }
                }

                @Override
                public void onFailure(Call<ConfirmClientOrderResponse> call, Throwable t) {
                    pDialog.hide();

                }
            });
        }else  if (type.equals("decline")){
            final ProgressDialog pDialog = new ProgressDialog(mCtx);
            pDialog.setMessage("Declining...");
            pDialog.show();
            Call<DeclineOrderResponse> call = apiInterface.DeclineOrder(api_token,order_id);
            call.enqueue(new Callback<DeclineOrderResponse>() {
                @Override
                public void onResponse(Call<DeclineOrderResponse> call, Response<DeclineOrderResponse> response) {
                    //get response values
                    if(response.isSuccessful()){
                        pDialog.hide();
                        String msg = response.body().getMsg();
                        new CallToast(mCtx,msg);
                    }else {

                    }
                }

                @Override
                public void onFailure(Call<DeclineOrderResponse> call, Throwable t) {
                    pDialog.hide();

                }
            });
        }

    }


}