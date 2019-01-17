package com.examlpe.com.sofra.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.com.sofra.ui.activities.FoodOrderActivity;
import com.examlpe.com.sofra.ui.activities.FoodSellActivity;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerUser;
import com.examlpe.com.sofra.data.model.client.ClientLoginResponse;
import com.examlpe.com.sofra.data.model.client.ClientUser;
import com.examlpe.com.sofra.data.model.general.City;
import com.examlpe.com.sofra.data.model.general.ClientRegister.ClientRegisterResponse;
import com.examlpe.com.sofra.data.model.general.Region;
import com.examlpe.com.sofra.data.model.restaurant.RestaurantLoginResponse;
import com.examlpe.com.sofra.data.model.restaurant.RestaurantUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_ALARMS;
import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_HOME;
import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_ORDER_DETAILS;

public class LoginMethod {
    private static LoginMethod mInstance;
    private static Context mCtx;

    private LoginMethod(Context context) {
        mCtx = context;
    }

    public static synchronized LoginMethod getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LoginMethod(context);
        }
        return mInstance;
    }
    //login method
    //type is to check client login or restaurant login
    //settingType is to check when it is in client size we opened the fragment from settings icon or cart fragment
    public void login(String email, String password, int type, final int settingType, final Context mContext){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        //declare a progress dialog
        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Login...");
        if(type==0){
            pDialog.show();
            Call<ClientLoginResponse> call = apiInterface.UserLogin(email , password);
            call.enqueue(new Callback<ClientLoginResponse>() {
                @Override
                public void onResponse(Call<ClientLoginResponse> call, Response<ClientLoginResponse> response) {
                    pDialog.hide();
                    //get response values
                    ClientLoginResponse login_response = response.body();
                    Integer status = login_response.getStatus();
                    String msg = login_response.getMsg();
                    if (status==0){
                        Toast.makeText(mContext,msg , Toast.LENGTH_LONG).show();
                    } else if (status==1){
                        String api_token =login_response.getData().getApiToken();
                        int id = login_response.getData().getUser().getId();
                        String name = login_response.getData().getUser().getName();
                        String email = login_response.getData().getUser().getEmail();
                        String phone = login_response.getData().getUser().getPhone();
                        String address = login_response.getData().getUser().getAddress();
                        //storing the api_token in shared preferences
                        SharedPrefManagerUser.getInstance(mContext).setKeyApiToken(api_token);
                        ClientUser user = new ClientUser(id,name,email,phone,address);
                        //storing the user in shared preferences
                        SharedPrefManagerUser.getInstance(mContext).setUser(user);
                        //show message
                        Toast.makeText(mContext,msg , Toast.LENGTH_LONG).show();
                        if (settingType==0){
                            //start home fragment
                            SetCurrentFragment.getInstance(mContext).setCurrentFragment(mContext, TAG_HOME, 0);

                            ((FoodOrderActivity) mContext).displayName();

                        }else if(settingType==1){
                            //start orderDetails fragment
                            SetCurrentFragment.getInstance(mContext).setCurrentFragment(mContext, TAG_ORDER_DETAILS, 13);
                            ((FoodOrderActivity) mContext).displayName();
                        }else if(settingType==2){
                            //start notifications fragment
                            SetCurrentFragment.getInstance(mContext).setCurrentFragment(mContext, TAG_ALARMS, 2);
                            ((FoodOrderActivity) mContext).displayName();
                        }

                    }

                }

                @Override
                public void onFailure(Call<ClientLoginResponse> call, Throwable t) {
                }
            });
        }

        else if(type==1){
            pDialog.show();
            Call<RestaurantLoginResponse> call = apiInterface.RestaurantLogin(email , password);
            call.enqueue(new Callback<RestaurantLoginResponse>() {
                @Override
                public void onResponse(Call<RestaurantLoginResponse> call, Response<RestaurantLoginResponse> response) {
                    pDialog.hide();
                    //get response values
                    RestaurantLoginResponse login_response = response.body();
                    Integer status = login_response.getStatus();
                    String msg = login_response.getMsg();
                    if (status==0){
                        Toast.makeText(mContext,msg , Toast.LENGTH_LONG).show();
                    } else if (status==1){
                        String api_token =login_response.getData().getApiToken();
                        String id =login_response.getData().getUser().getPhoto();
                        new CallToast(mContext,id);

                        //storing the api_token in shared preferences
                        SharedPrefManagerOwner.getInstance(mContext).setKeyApiToken(api_token);
                        RestaurantUser user = new RestaurantUser(
                                login_response.getData().getUser().getId(),
                                login_response.getData().getUser().getName(),
                                login_response.getData().getUser().getEmail(),
                                login_response.getData().getUser().getDeliveryCost(),
                                login_response.getData().getUser().getMinimumCharger(),
                                login_response.getData().getUser().getPhone(),
                                login_response.getData().getUser().getPhoto(),
                                login_response.getData().getUser().getAvailability(),
                                login_response.getData().getUser().getRate());
                        Region region = new Region(
                                login_response.getData().getUser().getRegion().getName());
                        City city = new City(
                                login_response.getData().getUser().getRegion().getCity().getName());

                        //storing the user in shared preferences
                        SharedPrefManagerOwner.getInstance(mContext).setUser(user);
                        //storing the region in shared preferences
                        SharedPrefManagerOwner.getInstance(mContext).setRegion(region);
                        //storing the city in shared preferences
                        SharedPrefManagerOwner.getInstance(mContext).setKeyCityName(city);
                        //show message
                        Toast.makeText(mContext,msg , Toast.LENGTH_LONG).show();
                        if (settingType==0){
                            //start home fragment
                            SetCurrentFragment.getInstance(mContext).setCurrentFragment(mContext, TAG_HOME, 0);
                            ((FoodSellActivity) mContext).displayName();
                        }else if(settingType==2){
                            //start notifications fragment
                            SetCurrentFragment.getInstance(mContext).setCurrentFragment(mContext, TAG_ALARMS, 3);
                            ((FoodSellActivity) mContext).displayName();
                        }

                    }
                }

                @Override
                public void onFailure(Call<RestaurantLoginResponse> call, Throwable t) {
                }
            });
        }

    }

    //Register method
    //type is to check client Register or restaurant Register
    //settingType is to check when it is in client size we opened the fragment from settings icon or cart fragment
    public void Register(EditText editTextName, EditText editTextEmail, EditText editTextPassword, EditText editTextPassword_confirmation,
                         EditText editTextPhone,EditText editTextAddress, TextView TextViewRegion_id,
                         int type, final int settingType, final Context mContext){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        //declare a progress dialog
        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Register...");
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String password_confirmation = editTextPassword_confirmation.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final String region_id = TextViewRegion_id.getText().toString().trim();

        if(type==0){
            pDialog.show();
            Call<ClientRegisterResponse> call = apiInterface.Register(name,email,password,password_confirmation,phone,
                    address,region_id);
            call.enqueue(new Callback<ClientRegisterResponse>() {
                @Override
                public void onResponse(Call<ClientRegisterResponse> call, Response<ClientRegisterResponse> response) {
                    pDialog.hide();
                    if(response.isSuccessful()){
                        //get response values
                        ClientRegisterResponse login_response = response.body();
                        String msg = login_response.getMsg();
                        new CallToast(mContext,msg);

                        String api_token =login_response.getData().getApiToken();
                        int id = login_response.getData().getUser().getId();
                        String name = login_response.getData().getUser().getName();
                        String email = login_response.getData().getUser().getEmail();
                        String phone = login_response.getData().getUser().getPhone();
                        String address = login_response.getData().getUser().getAddress();
                        //storing the api_token in shared preferences
                        SharedPrefManagerUser.getInstance(mContext).setKeyApiToken(api_token);
                        ClientUser user = new ClientUser(id,name,email,phone,address);
                        //storing the user in shared preferences
                        SharedPrefManagerUser.getInstance(mContext).setUser(user);
                        //show message
                        Toast.makeText(mContext,msg , Toast.LENGTH_LONG).show();
                        if (settingType==0){
                            //start home fragment
                            SetCurrentFragment.getInstance(mContext).setCurrentFragment(mContext, TAG_HOME, 0);
                            ((FoodOrderActivity) mContext).displayName();

                        }else if(settingType==1){
                            //start orderDetails fragment
                            SetCurrentFragment.getInstance(mContext).setCurrentFragment(mContext, TAG_ORDER_DETAILS, 13);

                            ((FoodOrderActivity) mContext).displayName();
                        }else if(settingType==2){
                            //start notifications fragment
                            SetCurrentFragment.getInstance(mContext).setCurrentFragment(mContext, TAG_ALARMS, 2);
                            ((FoodOrderActivity) mContext).displayName();
                        }
                    }else {
                        new CallToast(mContext,response.toString());
                    }
                }

                @Override
                public void onFailure(Call<ClientRegisterResponse> call, Throwable t) {
                }
            });
        }
    }



}
