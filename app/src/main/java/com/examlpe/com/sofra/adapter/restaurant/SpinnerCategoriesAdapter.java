package com.examlpe.com.sofra.adapter.restaurant;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.examlpe.com.sofra.data.model.general.Categories.Datum;
import com.examlpe.com.sofra.R;

import java.util.List;


public class SpinnerCategoriesAdapter extends ArrayAdapter<Datum> {

    private List<Datum> CategoriesList;


    private  Context context;

    private SpinnerCategoriesAdapter myAdapter;

    public SpinnerCategoriesAdapter(Context context, List<Datum> CategoriesList ) {

        super(context, R.layout.item_categories_spinner, CategoriesList);

        this.context = context;
        this.CategoriesList = CategoriesList;
        this.myAdapter = this;

    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    public View getCustomView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
        final TextView textViewId,textViewName;
        CheckBox checkbox;

        final int pos = position;
        rowView = inflater.inflate(R.layout.item_categories_spinner, parent, false);
        checkbox = (CheckBox) rowView.findViewById(R.id.checkbox);
        textViewId = (TextView) rowView.findViewById(R.id.textViewId);
        textViewName = (TextView) rowView.findViewById(R.id.textViewName);


        textViewId.setText(CategoriesList.get(position).getId());

        textViewName.setText(CategoriesList.get(position).getName());

        checkbox.setChecked(CategoriesList.get(position).isSelected());

        checkbox.setTag(CategoriesList.get(position));


        if ((position == 0)) {
           // checkbox.setVisibility(View.GONE);
            textViewId.setVisibility(View.GONE);
        } else {
            checkbox.setVisibility(View.VISIBLE);
        }

        checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Datum category = (Datum) cb.getTag();

                category.setSelected(cb.isChecked());
                CategoriesList.get(pos).setSelected(cb.isChecked());

            }
        });

        return rowView;
    }



    // method to access in activity after updating selection
    public List<Datum> getCategoriesList() {
        return CategoriesList;
    }
    // Return the size arraylist
    public int getItemCount() {
        return CategoriesList.size();
    }

}
