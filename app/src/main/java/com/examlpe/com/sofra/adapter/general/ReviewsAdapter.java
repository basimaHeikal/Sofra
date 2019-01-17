package com.examlpe.com.sofra.adapter.general;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.examlpe.com.sofra.data.model.general.reviews.Datum;
import com.examlpe.com.sofra.R;

import java.util.List;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.SensorViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the reviews in a list
    private List<Datum> reviewList;


    //getting the context and product list with constructor
    public ReviewsAdapter(Context mCtx, List<Datum> SensorList) {
        this.mCtx = mCtx;
        this.reviewList =SensorList;

    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        //This method returns a new instance of our ViewHolder.
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_fragment_reviews_tab, null);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SensorViewHolder holder, final int position) {
        //This method binds the data to the view holder.
        //getting the product of the specified position
        final Datum restaurant = reviewList.get(position);
        // holder.currentItem = items.get(position);
        //binding the data with the view holder views
        holder.TextViewCustomerName.setText(restaurant.getClient().getName());
        holder.textViewCustomerReview.setText(restaurant.getComment());

        String ss = restaurant.getCreatedAt().toString();
        String[] separated = ss.split(" ");
        holder.textViewReviewTime.setText(separated[0]);
        holder.ratingBar.setRating(Float.parseFloat(restaurant.getRate()));


    }


    @Override
    public int getItemCount() {
        //This returns the size of the List.
        return reviewList.size();
    }

    /*represents the views of our RecyclerView*/
    class SensorViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ClipData.Item currentItem;
        TextView TextViewCustomerName,textViewCustomerReview,textViewReviewTime;
        RatingBar ratingBar;


        public SensorViewHolder(View itemView) {
            super(itemView);

            TextViewCustomerName = itemView.findViewById(R.id.TextViewCustomerName);
            textViewCustomerReview = itemView.findViewById(R.id.textViewCustomerReview);
            textViewReviewTime = itemView.findViewById(R.id.textViewReviewTime);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }
}