package com.examlpe.com.sofra.data.local.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "SharedUser";
    private static final String KEY_ORDER_SELL = "keyOrderSell";
    private static final String KEY_ITEM_ID  = "keyFoodMenu";
    private static final String KEY_ADD= "keyAdd";
    private static final String KEY_RESTAURANT_ID = "basima";
    private static final String KEY_DIALOG = "whdud";
    private static final String KEY_TOTAL = "ajoaj";

    private static final String KEY_LOGIN= "keyLogin";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    public void setKeyOrderSell(String  orderSell) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ORDER_SELL, orderSell);
        editor.apply();
    }
    public String getKeyOrderSell() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ORDER_SELL, null);
    }



    public void setKeyAdd(String add) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ADD, add);
        editor.apply();
    }
    public String getKeyAdd() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADD, null);
    }


    public void setKeyTotal(String add) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOTAL, add);
        editor.apply();
    }
    public String getKeyTotal() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOTAL, null);
    }


    public void setKeyRestaurantId(String restaurantId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_RESTAURANT_ID, restaurantId);
        editor.apply();
    }
    public String getKeyRestaurantId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_RESTAURANT_ID, null);

    }

    public void setKEY_DIALOG(String restaurantId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_DIALOG, restaurantId);
        editor.apply();
    }
    public String getKEY_DIALOG() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DIALOG, null);

    }
    public void setKeyLogin(String login) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN, login);
        editor.apply();
    }
    public String getKeyLogin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LOGIN, null);
    }
}