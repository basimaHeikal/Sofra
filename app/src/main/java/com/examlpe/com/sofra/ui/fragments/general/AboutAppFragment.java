package com.examlpe.com.sofra.ui.fragments.general;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.examlpe.com.sofra.data.model.general.Categories.Datum;
import com.examlpe.com.sofra.R;
import com.examlpe.com.sofra.adapter.restaurant.SpinnerCategoriesAdapter;

import java.util.ArrayList;
import java.util.List;


public class AboutAppFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_about_app, container, false);

        Spinner spinner = (Spinner)rootView.findViewById(R.id.spinner);
        Button btnSelection = (Button)rootView.findViewById(R.id.btnSelection);

        ArrayList<Datum> categoriesList = new ArrayList<>();
        Datum st1 = new Datum("0" , "التصنيفات", false);
        categoriesList.add(st1);

        for (int i = 1; i <= 15; i++) {
            Datum st = new Datum(String.valueOf(i) , "tafida" + i, false);

            categoriesList.add(st);
        }
        final SpinnerCategoriesAdapter myAdapter = new SpinnerCategoriesAdapter(getActivity(), categoriesList);
        spinner.setAdapter(myAdapter);

        btnSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<String> data = new ArrayList<String>();
                List<Datum> categoryList = ((SpinnerCategoriesAdapter) myAdapter).getCategoriesList();

                for (int i = 0; i < categoryList.size(); i++) {
                    Datum category = categoryList.get(i);
                    if (category.isSelected() == true) {

                        data.add(category.getId().toString());
                    }

                }

                Toast.makeText(getActivity(),String.valueOf(data), Toast.LENGTH_LONG).show();
            }
        });


        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("عن التطبيق");
    }
}