package com.examlpe.com.sofra.helper;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.data.model.general.CitiesList.CitiesListResponse;
import com.examlpe.com.sofra.data.model.general.CitiesList.Datum;
import com.examlpe.com.sofra.data.model.general.RegionList.RegionListResponse;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCitiesMethod {

    private static GetCitiesMethod mInstance;
    private static Context context;

    private GetCitiesMethod(Context context) {
        context = context;
    }

    public static synchronized GetCitiesMethod getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new GetCitiesMethod(context);
        }
        return mInstance;
    }
    public void getCities(final Context mContext, final List<String>cityList, final HashMap<String, String> hashMap , final Spinner spinnerCity) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CitiesListResponse> call = apiInterface.getCitiesList();
        call.enqueue(new Callback<CitiesListResponse>() {
            @Override
            public void onResponse(Call<CitiesListResponse> call, Response<CitiesListResponse> response) {
                //get response values

                if(response.isSuccessful()){
                    List<Datum> listDatum = response.body().getData().getData();
                    for (int i = 0; i < listDatum.size(); i++){
                        String name = listDatum.get(i).getName();
                        String id =String.valueOf(listDatum.get(i).getId());;
                        cityList.add(name);
                        hashMap.put(name,id);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, cityList);
                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spinnerCity.setAdapter(adapter);


                }else {

                }
            }

            @Override
            public void onFailure(Call<CitiesListResponse> call, Throwable t) {
            }
        });
    }

    public void getRegion(final Context mContext , final TextView texViewRegion, final TextView textViewRegionId, final String id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<RegionListResponse> call = apiInterface.getRegionsList(id);
        call.enqueue(new Callback<RegionListResponse>() {
            @Override
            public void onResponse(Call<RegionListResponse> call, Response<RegionListResponse> response) {
                //get response values

                if(response.isSuccessful()){
                    List<com.examlpe.com.sofra.data.model.general.RegionList.Datum> listDatum = response.body().getData().getData();
                    for (int i = 0; i < listDatum.size(); i++){
                        String name = listDatum.get(i).getName();
                        String RegionId =String.valueOf(listDatum.get(i).getId());
                        texViewRegion.setText(name);
                        textViewRegionId.setText(RegionId);
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<RegionListResponse> call, Throwable t) {
            }
        });
    }

}
