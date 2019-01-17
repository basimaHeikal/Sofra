package com.examlpe.com.sofra.ui.fragments.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.examlpe.com.sofra.data.local.room.ItemFoodData;
import com.examlpe.com.sofra.data.local.room.RoomDao;
import com.examlpe.com.sofra.data.local.room.RoomManger;
import com.examlpe.com.sofra.helper.SetCurrentFragment;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_CART;

@SuppressLint("ValidFragment")
public class SandwichFragment extends Fragment {


    private LinearLayout linearLayoutAddToCart;
    private TextView textViewSandwichName,textViewSandwichDetails,textViewSandwichPriceValue,textViewPreparingTimeValue,textViewPhotoUrl;
    private EditText editTextMessage,editTextQuantity;
    private ImageButton imageButtonDecrease,imageButtonIecrease;
    private ImageView imageViewRestaurant;


    public int item_id ;
    public String item_name ;
    public String item_description ;
    public String item_price ;
    public String item_preparing_time ;
    public String item_photo ;

    @SuppressLint("ValidFragment")
    public SandwichFragment(int item_id, String item_name, String item_description, String item_price, String item_preparing_time, String item_photo) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_description = item_description;
        this.item_price = item_price;
        this.item_preparing_time = item_preparing_time;
        this.item_photo = item_photo;
    }

    String restaurant_id;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_sandwich, container, false);


        initElements(rootView);


        textViewSandwichName.setText(item_name);
        textViewSandwichDetails.setText(item_description);
        textViewSandwichPriceValue.setText(item_price);
        textViewPreparingTimeValue.setText(item_preparing_time);
        //loading the image
        Glide.with(getActivity())
                .load(item_photo)
                .into(imageViewRestaurant);
        restaurant_id = SharedPrefManager.getInstance(getActivity()).getKeyRestaurantId();

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(item_name);
    }

    private void initElements(View rootView){
        textViewSandwichName = (TextView) rootView.findViewById(R.id.textViewSandwichName);
        textViewSandwichDetails = (TextView) rootView.findViewById(R.id.textViewSandwichDetails);
        textViewSandwichPriceValue = (TextView) rootView.findViewById(R.id.textViewSandwichPriceValue);
        textViewPreparingTimeValue = (TextView) rootView.findViewById(R.id.textViewPreparingTimeValue);
        textViewPhotoUrl = (TextView) rootView.findViewById(R.id.textViewPhotoUrl);
        editTextMessage = (EditText) rootView.findViewById(R.id.editTextMessage);
        editTextQuantity = (EditText) rootView.findViewById(R.id.editTextQuantity);
        imageButtonDecrease = (ImageButton) rootView.findViewById(R.id.imageButtonDecrease);
        imageButtonIecrease = (ImageButton) rootView.findViewById(R.id.imageButtonIecrease);
        imageViewRestaurant = (ImageView) rootView.findViewById(R.id.imageViewRestaurant);
        linearLayoutAddToCart = (LinearLayout) rootView.findViewById(R.id.linearLayoutAddToCart);
        editTextQuantity.setText("1");

        linearLayoutAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item_note = editTextMessage.getText().toString();
                String quantity=editTextQuantity.getText().toString();

                final ItemFoodData itemFood = new ItemFoodData(item_id,item_note,quantity,item_name,item_price,item_photo,restaurant_id);

                RoomManger roomManger = RoomManger.getInstance(getActivity());
                final RoomDao roomDao = roomManger.roomDao();

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<ItemFoodData> foodItems = new ArrayList<>();

                        foodItems = roomDao.getAllItem();

                        roomDao.insertItemToCar(itemFood);

                        foodItems = roomDao.getAllItem();

                    }
                });
                SetCurrentFragment.getInstance(getActivity()).setCurrentFragment(getActivity(),TAG_CART,10);

            }
        });

        imageButtonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int val = Integer.parseInt(editTextQuantity.getText().toString());
                val--;
                editTextQuantity.setText(String.valueOf(val));
            }
        });

        imageButtonIecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int val = Integer.parseInt(editTextQuantity.getText().toString());
                val++;
                editTextQuantity.setText(String.valueOf(val));

            }
        });

    }

}
