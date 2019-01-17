package com.examlpe.com.sofra.ui.fragments.client;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.examlpe.com.sofra.adapter.client.CartAdapter;
import com.examlpe.com.sofra.data.local.room.ItemFoodData;
import com.examlpe.com.sofra.data.local.room.RoomDao;
import com.examlpe.com.sofra.data.local.room.RoomManger;
import com.examlpe.com.sofra.helper.SetCurrentFragment;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerUser;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_FOOD_MENU;
import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_LOGIN;
import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_ORDER_DETAILS;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {



    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.buttonConfirmOrder)
    Button buttonConfirmOrder;
    @BindView(R.id.buttonAddMore)
    Button buttonAddMore;
    @BindView(R.id.textViewTotalValue)
    TextView textViewTotalValue;
    Unbinder unbinder;

    private CartAdapter adapter;
    //a list to store all the products
    List<ItemFoodData> foodItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        loadCartElements();

        return rootView;
    }

    private void loadCartElements(){
        RoomManger roomManger = RoomManger.getInstance(getActivity());
        final RoomDao roomDao = roomManger.roomDao();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        foodItems = new ArrayList<>();

        foodItems = roomDao.getAllItem();

        //creating adapter object and setting it to recyclerView
        adapter = new CartAdapter(getActivity(), foodItems, textViewTotalValue);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("سلة الطلبات");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.buttonConfirmOrder)
    public void onButtonConfirmOrderClicked() {
        SharedPrefManager.getInstance(getActivity()).setKeyLogin("CONFIRM_ORDER");
        //if user is not logged in ==> open login fragment
        if (!SharedPrefManagerUser.getInstance(getActivity()).isLoggedIn()) {
            SetCurrentFragment.getInstance(getActivity()).setCurrentFragment(getActivity(), TAG_LOGIN, 11);

        }
        //if user is logged in ==>confirm order
        else {
            SetCurrentFragment.getInstance(getActivity()).setCurrentFragment(getActivity(), TAG_ORDER_DETAILS, 13);
        }
    }

    @OnClick(R.id.buttonAddMore)
    public void onButtonAddMoreClicked() {
        SetCurrentFragment.getInstance(getActivity()).setCurrentFragment(getActivity(), TAG_FOOD_MENU, 8);
    }
}
