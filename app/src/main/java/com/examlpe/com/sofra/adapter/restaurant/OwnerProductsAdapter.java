package com.examlpe.com.sofra.adapter.restaurant;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.examlpe.com.sofra.helper.URLs;
import com.examlpe.com.sofra.data.model.restaurant.FoodMenu.Datum;
import com.examlpe.com.sofra.R;

import java.util.List;


public class OwnerProductsAdapter extends RecyclerView.Adapter<OwnerProductsAdapter.SensorViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Datum> SensorList;



    //getting the context and product list with constructor
    public OwnerProductsAdapter(Context mCtx, List<Datum> SensorList) {
        this.mCtx = mCtx;
        this.SensorList = SensorList;

    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        //This method returns a new instance of our ViewHolder.
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_fragment_owner_products, null);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SensorViewHolder holder, final int position) {
        //This method binds the data to the view holder.
        //getting the product of the specified position
        final Datum restaurant = SensorList.get(position);
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
        }
    }
}