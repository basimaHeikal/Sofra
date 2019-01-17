package com.examlpe.com.sofra.ui.fragments.general;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;

import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;


public class ShareAppFragment extends Fragment {

    private boolean[] checkedItems ;
    private String[] colors;
    private List<String> selectedColors = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_share_app, container, false);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("شارك التطبيق");
    }
}
