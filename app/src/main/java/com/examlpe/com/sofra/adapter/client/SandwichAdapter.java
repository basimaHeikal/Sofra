package com.examlpe.com.sofra.adapter.client;


import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.examlpe.com.sofra.ui.activities.FoodOrderActivity;
import com.examlpe.com.sofra.ui.fragments.general.FoodMenuTab;
import com.examlpe.com.sofra.helper.SetCurrentFragment;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.helper.URLs;
import com.examlpe.com.sofra.data.model.restaurant.FoodMenu.Datum;
import com.examlpe.com.sofra.R;

import java.util.List;

import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_SANDWICH;

public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.SensorViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Datum> SensorList;

    private FoodMenuTab fragment;

    Datum restaurant;


    //getting the context and product list with constructor
    public SandwichAdapter(Context mCtx, List<Datum> SensorList,FoodMenuTab fragment) {
        this.mCtx = mCtx;
        this.SensorList = SensorList;
        this.fragment = fragment;
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        //This method returns a new instance of our ViewHolder.
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_fragment_food_menu_tab, null);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SensorViewHolder holder, final int position) {
        //This method binds the data to the view holder.
        //getting the product of the specified position
        restaurant = SensorList.get(position);
        // holder.currentItem = items.get(position);
        //binding the data with the view holder views
        holder.textViewSandwichName.setText(restaurant.getName());
        holder.textViewSandwichDetails.setText(restaurant.getDescription());
        holder.textViewSandwichPriceValue.setText(restaurant.getPrice());

        String photo = URLs.PHOTO_DOMAIN +restaurant.getPhoto();

        //loading the image
       Glide.with(mCtx)
                .load(photo)
                .into(holder.imageViewRestaurant);



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
        TextView textViewSandwichName, textViewSandwichDetails, textViewSandwichPriceValue;
        ImageView imageViewRestaurant;

        public SensorViewHolder(View itemView) {
            super(itemView);

            textViewSandwichName = itemView.findViewById(R.id.textViewSandwichName);
            textViewSandwichDetails = itemView.findViewById(R.id.textViewSandwichDetails);
            textViewSandwichPriceValue = itemView.findViewById(R.id.textViewSandwichPriceValue);

            imageViewRestaurant = itemView.findViewById(R.id.imageViewRestaurant);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //getting orderSell value
                    String orderSell = SharedPrefManager.getInstance(mCtx).getKeyOrderSell();

                    if (orderSell.equals("SELL") ){

                    }else if(orderSell.equals("ORDER")){
                        //start sandwich fragment
                        //put extra id

                        FoodOrderActivity.item_id =restaurant.getId();
                        FoodOrderActivity.item_name =restaurant.getName();
                        FoodOrderActivity.item_description =restaurant.getDescription();
                        FoodOrderActivity.item_price =restaurant.getPrice();
                        FoodOrderActivity.item_preparing_time =restaurant.getPreparingTime();
                        FoodOrderActivity.item_photo =restaurant.getPhotoUrl();


                        //starting food menu fragment
                        SetCurrentFragment.getInstance(mCtx).setCurrentFragment(mCtx,TAG_SANDWICH,9);
                    }

                }
            });
        }
    }
}