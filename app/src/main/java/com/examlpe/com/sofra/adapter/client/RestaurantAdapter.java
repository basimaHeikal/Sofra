package com.examlpe.com.sofra.adapter.client;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.examlpe.com.sofra.ui.fragments.client.HomeFragment;
import com.examlpe.com.sofra.helper.SetCurrentFragment;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.model.general.RestaurantList.Category;
import com.examlpe.com.sofra.data.model.general.RestaurantList.Datum;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_FOOD_MENU;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the Restaurants in a list
    private List<Datum> RestaurantList;

    private List<Category> foodList;

    //object of home fragment
    private HomeFragment fragment;


    //getting the context and Restaurants list with constructor
    public RestaurantAdapter(Context mCtx, List<Datum> SensorList, HomeFragment fragment) {
        this.mCtx = mCtx;
        this.RestaurantList = SensorList;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        //This method returns a new instance of our ViewHolder.
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_fragment_home, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //initializing the sensorList
        foodList = new ArrayList<>();
        //This method binds the data to the view holder.
        //getting the product of the specified position
        final Datum restaurant = RestaurantList.get(position);
        // holder.currentItem = items.get(position);
        //binding the data with the view holder views
        holder.textViewRestaurantName.setText(restaurant.getName());

        foodList = restaurant.getCategories();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < foodList.size(); i++) {
            sb.append(foodList.get(i).getName());
            sb.append("  ");
        }
        holder.textViewRestaurantType.setText(sb);

        holder.textViewState.setText(restaurant.getAvailability());
        holder.textViewLowValue.setText(restaurant.getMinimumCharger());
        holder.textViewDeliveryValue.setText(restaurant.getDeliveryCost());
        holder.textViewId.setText(String.valueOf(restaurant.getId()));
        holder.ratingBar.setRating(Float.parseFloat(restaurant.getRate()));


        //loading the image
        Glide.with(mCtx)
                .load(restaurant.getPhotoUrl())
                .into(holder.imageViewRestaurant);


    }


    @Override
    public int getItemCount() {
        //This returns the size of the List.
        return RestaurantList.size();
    }

    /*represents the views of our RecyclerView*/
    class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ClipData.Item currentItem;
        @BindView(R.id.imageViewRestaurant)
        ImageView imageViewRestaurant;
        @BindView(R.id.textViewRestaurantName)
        TextView textViewRestaurantName;
        @BindView(R.id.textViewRestaurantType)
        TextView textViewRestaurantType;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.textViewState)
        TextView textViewState;
        @BindView(R.id.textViewLowValue)
        TextView textViewLowValue;
        @BindView(R.id.textViewLow)
        TextView textViewLow;
        @BindView(R.id.textViewDeliveryValue)
        TextView textViewDeliveryValue;
        @BindView(R.id.textViewDelivery)
        TextView textViewDelivery;
        @BindView(R.id.textViewId)
        TextView textViewId;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);


            view = itemView;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //put extra id
                    int restaurant_id = Integer.parseInt(textViewId.getText().toString());
                    SharedPrefManager.getInstance(mCtx).setKeyRestaurantId(String.valueOf(restaurant_id));
//                    new CallToast(mCtx,String.valueOf(restaurant_id));
                    //starting food menu fragment
                    SetCurrentFragment.getInstance(mCtx).setCurrentFragment(mCtx, TAG_FOOD_MENU, 8);


                }
            });
        }

    }}