package com.examlpe.com.sofra.ui.fragments.client;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.examlpe.com.sofra.helper.GetCitiesMethod;
import com.examlpe.com.sofra.helper.MyClickListener;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientSignFragment extends Fragment {


    Spinner spinnerCity;
    List<String> cityList;
    EditText editTextName, editTextEmail,editTextPhone,editTextPlace,editTextPass,editTextRetypePass;

    Button buttonClientSign;
    private TextView textViewCity,texViewRegion,textViewRegionId;
    // a simple hashMap declaration with default size and load factor
    HashMap<String, String> hashMapCities;
    HashMap<String, String> hashMapRegion;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_client_sign, container, false);

        intElements(rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("إنشاء حساب جديد");
    }

    private void intElements(View rootView) {
        // Spinner element
        spinnerCity = (Spinner) rootView.findViewById(R.id.spinnerCity);
        textViewCity = (TextView) rootView.findViewById(R.id.textViewCity);
        texViewRegion = (TextView) rootView.findViewById(R.id.texViewRegion);
        textViewRegionId = (TextView) rootView.findViewById(R.id.textViewRegionId);
        editTextName = (EditText) rootView.findViewById(R.id.editTextName);
        editTextEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) rootView.findViewById(R.id.editTextPhone);
        editTextPlace = (EditText) rootView.findViewById(R.id.editTextPlace);
        editTextPass = (EditText) rootView.findViewById(R.id.editTextPass);
        editTextRetypePass = (EditText) rootView.findViewById(R.id.editTextRetypePass);
        buttonClientSign = (Button) rootView.findViewById(R.id.buttonClientSign);



        cityList = new ArrayList<String>();
        hashMapCities = new HashMap<String, String>();

        //call get cities method to load cities from server to spinner
        GetCitiesMethod.getInstance(getActivity()).getCities(getActivity(), cityList, hashMapCities, spinnerCity);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                //get city id from hashMap
                String cityId = (String) hashMapCities.get(item);
                //set city name to the textView
                textViewCity.setText(item);
                //get the region
                GetCitiesMethod.getInstance(getActivity()).getRegion(getActivity(),texViewRegion,textViewRegionId,cityId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //onClickListener for buttonClientSign
        buttonClientSign.setOnClickListener(new MyClickListener(getActivity(),"buttonClientSign","button",
                editTextName,
                editTextEmail,
                editTextPass,
                editTextRetypePass,
                editTextPhone,
                editTextPlace,
                textViewRegionId));

    }
}
