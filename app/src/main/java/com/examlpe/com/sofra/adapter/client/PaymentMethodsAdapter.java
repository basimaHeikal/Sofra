package com.examlpe.com.sofra.adapter.client;


import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.examlpe.com.sofra.data.model.general.paymentMethods.Datum;
import com.examlpe.com.sofra.R;

import java.util.List;

public class PaymentMethodsAdapter extends RecyclerView.Adapter<PaymentMethodsAdapter.SensorViewHolder> {
    public   TextView textViewId;
    Context context;
    List<Datum> paymentList;
    public int mSelectedItem = -1;

    public PaymentMethodsAdapter(Context context, List<Datum> paymentList, TextView textViewId) {
        this.context = context;
        this.paymentList = paymentList;
        this.textViewId=textViewId;
    }


    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment_methods, parent, false);

        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SensorViewHolder holder, int position) {
        final Datum restaurant = paymentList.get(position);
        // holder.currentItem = items.get(position);
        //binding the data with the view holder views
        holder.textViewName.setText(restaurant.getName());
        holder.textViewId.setText(restaurant.getId());
        holder.radioButton.setChecked(position == mSelectedItem);



        holder.loadData(position);

    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    /*represents the views of our RecyclerView*/
    class SensorViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ClipData.Item currentItem;
        TextView textViewName,textViewId;
        RadioButton radioButton;

        public SensorViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewId = itemView.findViewById(R.id.textViewId);
            radioButton=itemView.findViewById(R.id.radioButton);

            view = itemView;

        }
        void loadData(final int position) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    String id=paymentList.get(position).getId();
                    textViewId.setText(id);
                    notifyDataSetChanged();

//                    Toast.makeText(context,textViewId.getText().toString() ,Toast.LENGTH_SHORT).show();

                }
            };
            radioButton.setOnClickListener(listener);
            itemView.setOnClickListener(listener);
        }
    }

}