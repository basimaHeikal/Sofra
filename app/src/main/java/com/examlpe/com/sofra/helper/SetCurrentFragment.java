package com.examlpe.com.sofra.helper;

import android.content.Context;

import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.ui.activities.FoodOrderActivity;
import com.examlpe.com.sofra.ui.activities.FoodSellActivity;

public class SetCurrentFragment {
    private static SetCurrentFragment mInstance;
    private static Context context;

    private SetCurrentFragment(Context context) {
        context = context;
    }

    public static synchronized SetCurrentFragment getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SetCurrentFragment(context);
        }
        return mInstance;
    }
    public void setCurrentFragment (Context mContext,String current_tag,int nav_index){
        //getting the orderSell value
        String orderSell = SharedPrefManager.getInstance(mContext).getKeyOrderSell();

        if (orderSell.equals("SELL") ){
            //set variable CURRENT_TAG and navItemIndex
            FoodSellActivity.CURRENT_TAG = current_tag;
            FoodSellActivity.navItemIndex = nav_index;
            //load  fragment
            ((FoodSellActivity)mContext).loadHomeFragment();
        }else if(orderSell.equals("ORDER")){
            //set variable CURRENT_TAG and navItemIndex
            FoodOrderActivity.CURRENT_TAG = current_tag;
            FoodOrderActivity.navItemIndex = nav_index;
            //load  fragment
            ((FoodOrderActivity)mContext).loadHomeFragment();
        }
    }
}
