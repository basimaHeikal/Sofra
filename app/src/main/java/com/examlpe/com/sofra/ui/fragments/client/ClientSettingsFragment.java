package com.examlpe.com.sofra.ui.fragments.client;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examlpe.com.sofra.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientSettingsFragment extends Fragment {


    public ClientSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_settings, container, false);
    }

}
