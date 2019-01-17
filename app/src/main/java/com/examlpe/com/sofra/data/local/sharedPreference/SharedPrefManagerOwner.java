package com.examlpe.com.sofra.data.local.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.examlpe.com.sofra.data.model.general.City;
import com.examlpe.com.sofra.data.model.general.Region;
import com.examlpe.com.sofra.data.model.restaurant.RestaurantUser;

public class SharedPrefManagerOwner {
    //the constants
    private static final String SHARED_PREF_NAME = "SharedPrefOwner";
    private static final String KEY_API_TOKEN = "keyApiToken";
    private static final String KEY_ID = "keyId";
    private static final String KEY_NAME = "keyFirst";
    private static final String KEY_EMAIL = "keyEmail";
    private static final String KEY_PHONE = "keyPhone";
    private static final String KEY_REGION_NAME = "deliveryMethodId";
    private static final String KEY_CITY_NAME= "deliveryDays";
    private static final String KEY_DELIVERY_COST= "deliveryCost";
    private static final String KEY_MINIMUM_CHARGER = "minimumCharger";
    private static final String KEY_WHATSAPP = "whatsapp";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_AVAILABILITY = "availability";
    private static final String KEY_ACTIVATED = "activated";
    private static final String KEY_RATE = "jkj";


    private static SharedPrefManagerOwner mInstance;
    private static Context mCtx;

    private SharedPrefManagerOwner(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerOwner getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerOwner(context);
        }
        return mInstance;
    }

    public void setKeyApiToken(String  orderSell) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_API_TOKEN, orderSell);
        editor.apply();
    }
    public String getKeyApiToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_API_TOKEN, null);
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_API_TOKEN, null) != null;
    }

    //this method will store the user data in shared preferences
    public void setUser(RestaurantUser user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_DELIVERY_COST, user.getDeliveryCost());
        editor.putString(KEY_MINIMUM_CHARGER, user.getMinimumCharger());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_PHOTO, user.getPhoto());
        editor.putString(KEY_AVAILABILITY, user.getAvailability());
        editor.putString(KEY_RATE, user.getRate());
        editor.apply();
    }



    //this method will give the logged in user
    public RestaurantUser getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new RestaurantUser(
                sharedPreferences.getString( KEY_ID,null),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_DELIVERY_COST, null),
                sharedPreferences.getString(KEY_MINIMUM_CHARGER, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_PHOTO, null),
                sharedPreferences.getString(KEY_AVAILABILITY, null),
                sharedPreferences.getString( KEY_RATE,null)
        );
    }

    //this method will store the Region data in shared preferences
    public void setRegion(Region region) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_REGION_NAME, region.getName());
        editor.apply();
    }

    //this method will give the region
    public Region getRegion() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Region(
                sharedPreferences.getString(KEY_REGION_NAME, null)
        );
    }

    //this method will store the City data in shared preferences
    public void setKeyCityName (City city) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CITY_NAME, city.getName());
        editor.apply();
    }

    //this method will give the region
        public City getKeyCityName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new City(
                sharedPreferences.getString(KEY_CITY_NAME, null)
        );
    }
    //this method will logout the user and direct him to the logo activity
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
