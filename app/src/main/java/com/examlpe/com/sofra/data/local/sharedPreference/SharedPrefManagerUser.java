package com.examlpe.com.sofra.data.local.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.examlpe.com.sofra.data.model.client.ClientUser;

public class SharedPrefManagerUser {

    //the constants
    private static final String SHARED_PREF_NAME = "SharedPrefUser";
    private static final String KEY_API_TOKEN = "keyApiToken";
    private static final int KEY_ID = 0;
    private static final String KEY_NAME = "keyFirst";
    private static final String KEY_EMAIL = "keyEmail";
       private static final String KEY_PHONE = "keyPhone";
    private static final String KEY_ADDRESS = "keyAddress";

    private static SharedPrefManagerUser mInstance;
    private static Context mCtx;

    private SharedPrefManagerUser(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerUser getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerUser(context);
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
    public void setUser(ClientUser user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(String.valueOf(KEY_ID), user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.apply();
    }



    //this method will give the logged in user
    public ClientUser getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ClientUser(
                sharedPreferences.getInt(null, KEY_ID),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_ADDRESS, null)
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
