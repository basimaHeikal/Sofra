package com.examlpe.com.sofra.adapter.general;


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
import com.examlpe.com.sofra.data.model.general.OffersList.Datum;
import com.examlpe.com.sofra.R;

import java.util.List;

public class NewOffersAdapter extends RecyclerView.Adapter<NewOffersAdapter.SensorViewHolder> {


    private Context mCtx;

    //we are storing all the products in a list
    private List<Datum> SensorList;


    //getting the context and product list with constructor
    public NewOffersAdapter(Context mCtx, List<Datum> SensorList) {
        this.mCtx = mCtx;
        this.SensorList = SensorList;
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        //This method returns a new instance of our ViewHolder.
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_fragment_new_offers, null);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SensorViewHolder holder, final int position) {
        //This method binds the data to the view holder.
        //getting the product of the specified position
        final Datum restaurant = SensorList.get(position);
        // holder.currentItem = items.get(position);
        //binding the data with the view holder views
        holder.textViewRestaurantName.setText(restaurant.getRestaurant().getName());
        holder.textViewOfferName.setText(restaurant.getDescription());

        StringBuilder sb = new StringBuilder();
        sb.append(restaurant.getStartingAt().toString()) ;

       // String ss = restaurant.getStartingAt().toString();
        String[] separated = sb.toString().split(" ");
        holder.textViewOfferTime.setText( "من : "+ separated[0]);


        StringBuilder s = new StringBuilder();
        s.append(restaurant.getEndingAt().toString()) ;
        String[] separated1 = s.toString().split(" ");
        holder.textViewOfferTimeEnd.setText( "إالى : "+ separated1[0]);

        holder.textViewOfferPrice.setText( "السعر: " + restaurant.getPrice());

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
        TextView textViewOfferName, textViewRestaurantName, textViewOfferTime, textViewOfferPrice,textViewOfferTimeEnd;
        ImageView imageViewRestaurant;

        public SensorViewHolder(View itemView) {
            super(itemView);

            textViewRestaurantName = itemView.findViewById(R.id.textViewRestaurantName);
            textViewOfferName = itemView.findViewById(R.id.textViewOfferName);
            textViewOfferTime = itemView.findViewById(R.id.textViewOfferTime);
            textViewOfferPrice = itemView.findViewById(R.id.textViewOfferPrice);
            textViewOfferTimeEnd = itemView.findViewById(R.id.textViewOfferTimeEnd);
            imageViewRestaurant = itemView.findViewById(R.id.imageViewRestaurant);
            view = itemView;
            //when click on a sensor ==> open UserSensorState activity
            /*view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // item clicked
                    Intent intent1 = new Intent(mCtx, UserSensorState.class);
                    intent1.putExtra("sensorState", textViewSensorState.getText().toString());
                    intent1.putExtra("ImageUrl", textViewSensorImage.getText());
                    mCtx.startActivity(intent1);

                }
            });*/
        }
    }
}