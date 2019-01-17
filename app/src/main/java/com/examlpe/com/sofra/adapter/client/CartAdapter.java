package com.examlpe.com.sofra.adapter.client;


import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.examlpe.com.sofra.data.local.room.ItemFoodData;
import com.examlpe.com.sofra.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    private  TextView textViewTotalValue;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<ItemFoodData> SensorList;


    double all = 0.0;
    String t;


    //getting the context and product list with constructor
    public CartAdapter(Context mCtx, List<ItemFoodData> SensorList, TextView textViewTotalValue) {
        this.mCtx = mCtx;
        this.SensorList = SensorList;
        this.textViewTotalValue=textViewTotalValue;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        //This method returns a new instance of our ViewHolder.
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_fragment_cart, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //This method binds the data to the view holder.
        //getting the product of the specified position
        final ItemFoodData restaurant = SensorList.get(position);
        // holder.currentItem = items.get(position);
        //binding the data with the view holder views
        holder.textViewSandwichName.setText(restaurant.getName());
        holder.textViewSandwichPriceValue.setText(restaurant.getPrice());
        holder.editTextQuantity.setText(restaurant.getQuantity());


        Double price = Double.parseDouble(restaurant.getPrice());
        Double quantity = Double.parseDouble(restaurant.getQuantity());

        Double total =price*quantity;

        holder.textViewTotalValue.setText(String.valueOf(total));


        all = all + total;

        t = String.valueOf(all);
        textViewTotalValue.setText(t);
//        SharedPrefManager.getInstance(mCtx).setKeyTotal(t);
//        new CallToast(mCtx,t);


        //loading the image
       Glide.with(mCtx)
                .load(restaurant.getPhotoUrl())
                .into(holder.imageViewRestaurant);



    }


    @Override
    public int getItemCount() {
        //This returns the size of the List.
        return SensorList.size();
    }

    /*represents the views of our RecyclerView*/
    class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ClipData.Item currentItem;
        TextView textViewSandwichName, textViewSandwichPriceValue,textViewTotalValue;
        ImageView imageViewRestaurant;
        EditText editTextQuantity;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewSandwichName = itemView.findViewById(R.id.textViewSandwichName);
            textViewTotalValue = itemView.findViewById(R.id.textViewTotalValue);
            textViewSandwichPriceValue = itemView.findViewById(R.id.textViewSandwichPriceValue);
            imageViewRestaurant = itemView.findViewById(R.id.imageViewRestaurant);
            editTextQuantity = itemView.findViewById(R.id.editTextQuantity);
            view = itemView;
        }
    }



}