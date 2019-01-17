package com.examlpe.com.sofra.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerUser;
import com.examlpe.com.sofra.ui.activities.FoodOrderActivity;
import com.examlpe.com.sofra.ui.activities.FoodSellActivity;

import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_CLIENT_SETTINGS;
import static com.examlpe.com.sofra.ui.activities.FoodSellActivity.TAG_LOGIN;

public class MyClickListener implements View.OnClickListener {
    private Context context;
    private String name,viewType;
    private EditText userName,email,password,password_confirmation,address,phone;
    private TextView region_id;

    public MyClickListener(Context context , String name , String viewType) {
        this.context = context;
        this.name = name;
        this.viewType = viewType;
    }
    public MyClickListener(Context context , String name , String viewType, EditText userName, EditText email,
                           EditText password, EditText password_confirmation,EditText phone, EditText address, TextView region_id) {
        this.context = context;
        this.name = name;
        this.viewType = viewType;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.address = address;
        this.region_id = region_id;
        this.phone = phone;

    }
    @Override
    public void onClick(View view) {
        //if view is a button
        if(viewType.equals("button")){
            Button button = (Button) view;
            //splash activity , buttonOrder
            if(name.equals("buttonOrder")){
                SharedPrefManager.getInstance(context).setKeyOrderSell("ORDER");
                ((Activity)context).finish();
                context.startActivity(new Intent(context, FoodOrderActivity.class));
            }
            //splash activity , buttonSell
            else if(name.equals("buttonSell")){
                SharedPrefManager.getInstance(context).setKeyOrderSell("SELL");
                ((Activity)context).finish();
                context.startActivity(new Intent(context, FoodSellActivity.class));
            }
            //ClientSignFragment , buttonClientSign
            else if(name.equals("buttonClientSign")){
                //if it is a client
                ////get login key to check if user opened login fragment from settings icon or from cart icon
                String login = SharedPrefManager.getInstance(context).getKeyLogin();
                if (login.equals("CONFIRM_ORDER") ){
                    LoginMethod.getInstance(context).Register(userName,email,password,password_confirmation,phone,address
                            ,region_id,0,1,context);
                }else if(login.equals("SETTINGS")){
                    LoginMethod.getInstance(context).Register(userName,email,password,password_confirmation,phone,address
                            ,region_id,0,0,context);
                }else if(login.equals("ALARMS")){
                    LoginMethod.getInstance(context).Register(userName,email,password,password_confirmation,phone,address
                            ,region_id,0,2,context);
                }
            }
         //if view is an image
        }else if(viewType.equals("image")){
            ImageView button = (ImageView) view;
            //foodOrder activity , settings icon
            if(name.equals("order_settings")) {
                SharedPrefManager.getInstance(context).setKeyLogin("SETTINGS");
                SharedPrefManagerUser.getInstance(context).logout();
                if (!SharedPrefManagerUser.getInstance(context).isLoggedIn()) {
                    //setCurrentTag(TAG_LOGIN,11,1);
                    ((FoodOrderActivity)context).setCurrentTag(TAG_LOGIN,11,1);
                }else {
                    ((FoodOrderActivity)context).setCurrentTag(TAG_CLIENT_SETTINGS,14,1);
                }
            }
            //foodSell activity , settings icon
            else if(name.equals("sell_settings")){
                SharedPrefManager.getInstance(context).setKeyLogin("SETTINGS");
                SharedPrefManagerOwner.getInstance(context).logout();
                if (!SharedPrefManagerOwner.getInstance(context).isLoggedIn()) {
                    ((FoodSellActivity)context).setCurrentTag(TAG_LOGIN,12,1);
                }else {
                    //setCurrentTag(TAG_CLIENT_SETTINGS,14,1);
                }
            }else if(name.equals("imageViewAddPhoto")){

            }
        }




    }
}