package com.examlpe.com.sofra.adapter.general;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examlpe.com.sofra.data.model.general.NotificationsList.Datum;
import com.examlpe.com.sofra.R;

import java.util.List;

public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsAdapter.SensorViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Datum> SensorList;


    //getting the context and product list with constructor
    public AlarmsAdapter(Context mCtx, List<Datum> SensorList) {
        this.mCtx = mCtx;
        this.SensorList = SensorList;
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        //This method returns a new instance of our ViewHolder.
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_fragment_alarms, null);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SensorViewHolder holder, final int position) {
        //This method binds the data to the view holder.
        //getting the product of the specified position
        final Datum restaurant = SensorList.get(position);
        // holder.currentItem = items.get(position);
        //binding the data with the view holder views
        holder.textViewAlarmName.setText(restaurant.getTitle());
        String ss = restaurant.getCreatedAt().toString();
        String[] separated = ss.split(" ");
        holder.textViewAlarmDate.setText(separated[0]);
        holder.textViewAlarmTime.setText(separated[1]);


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
        TextView textViewAlarmName, textViewAlarmDate, textViewAlarmTime;


        public SensorViewHolder(View itemView) {
            super(itemView);

            textViewAlarmName = itemView.findViewById(R.id.textViewAlarmName);
            textViewAlarmDate = itemView.findViewById(R.id.textViewAlarmDate);
            textViewAlarmTime = itemView.findViewById(R.id.textViewAlarmTime);

            view = itemView;
        }
    }
}