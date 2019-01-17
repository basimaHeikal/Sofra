package com.examlpe.com.sofra.ui.fragments.restaurant;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.examlpe.com.sofra.helper.GetCitiesMethod;
import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.data.model.general.Categories.CategoriesResponse;
import com.examlpe.com.sofra.data.model.general.Categories.Datum;
import com.examlpe.com.sofra.R;
import com.examlpe.com.sofra.adapter.restaurant.SpinnerCategoriesAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class OwnerSignFragment extends Fragment {
    List<Datum> categoriesList;
    Spinner spinner;
    Spinner spinnerCity;
    List<String> cityList;
    List<String> data;
    HashMap<String, String> hashMapCities;
    SpinnerCategoriesAdapter myAdapter;
    Button buttonClientSign;

    EditText editTextName,editTextEmail,editTextPass,editTextRetypePass,editTextMinimumCharger,editTextDeliveryCost
             ,editTextPhone,editTextWhatsApp;
    ImageView imageViewAddPhoto;
    private TextView textViewCity, texViewRegion, textViewRegionId;

    Uri selectedImage;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_owner_sign, container, false);


        intElements(rootView);
        cityList = new ArrayList<String>();
        hashMapCities = new HashMap<String, String>();

        //call get cities method to load cities from server to spinner
        GetCitiesMethod.getInstance(getActivity()).getCities(getActivity(), cityList, hashMapCities, spinnerCity);

        clickListeners();

        getCategories();





        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("إنشاء حساب جديد");
    }

    private void intElements(View rootView) {
        spinner = (Spinner) rootView.findViewById(R.id.spinnerCategory);
        spinnerCity = (Spinner) rootView.findViewById(R.id.spinnerCity);
        buttonClientSign = (Button) rootView.findViewById(R.id.buttonClientSign);
        textViewCity = (TextView) rootView.findViewById(R.id.textViewCity);
        texViewRegion = (TextView) rootView.findViewById(R.id.texViewRegion);
        textViewRegionId = (TextView) rootView.findViewById(R.id.textViewRegionId);
        editTextName = (EditText) rootView.findViewById(R.id.editTextName);
        editTextEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
        editTextPass = (EditText) rootView.findViewById(R.id.editTextPass);
        editTextRetypePass = (EditText) rootView.findViewById(R.id.editTextRetypePass);
        editTextMinimumCharger = (EditText) rootView.findViewById(R.id.editTextMinimumCharger);
        editTextDeliveryCost = (EditText) rootView.findViewById(R.id.editTextDeliveryCost);
        editTextPhone = (EditText) rootView.findViewById(R.id.editTextPhone);
        editTextWhatsApp = (EditText) rootView.findViewById(R.id.editTextWhatsApp);
        imageViewAddPhoto = (ImageView) rootView.findViewById(R.id.imageViewAddPhoto);

    }

    private void clickListeners() {
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                //get city id from hashMap
                String cityId = (String) hashMapCities.get(item);
                //set city name to the textView
                textViewCity.setText(item);
                //get the region
                GetCitiesMethod.getInstance(getActivity()).getRegion(getActivity(), texViewRegion, textViewRegionId, cityId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonClientSign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = new ArrayList<String>();
                List<Datum> categoryList = ((SpinnerCategoriesAdapter) myAdapter).getCategoriesList();

                for (int i = 0; i < categoryList.size(); i++) {
                    Datum category = categoryList.get(i);
                    if (category.isSelected() == true) {

                        data.add(category.getId().toString());
                    }


                }

                Register(selectedImage);
            }
        });

        imageViewAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open gallery to select photo
                pickPhoto();
            }
        });

    }
    private void getCategories() {
        categoriesList = new ArrayList<>();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CategoriesResponse> call = apiInterface.getCategoriesList();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                //get response values
                if (response.isSuccessful()) {

                    categoriesList = response.body().getData();
                    myAdapter = new SpinnerCategoriesAdapter(getActivity(), categoriesList);
                    spinner.setAdapter(myAdapter);

                } else {

                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
            }
        });
    }

    public void pickPhoto(){
        //open gallery to select photo
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //the image URI

            selectedImage = data.getData();

            String s = getRealPathFromURI(selectedImage);
            imageViewAddPhoto.setImageBitmap(BitmapFactory.decodeFile(s));

        }
    }

    /*
     * This method is fetching the absolute path of the image file
     * if you want to upload other kind of files like .pdf, .docx
     * you need to make changes on this method only
     * Rest part will be the same
     * */
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getActivity(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    private void Register(Uri fileUri) {


        HashMap<String, String> categories= new HashMap<>();
        for (int i=0;i<data.size();i++){
            categories.put("categories["+i+"]", data.get(i));
        }


        new CallToast(getActivity(),categories.toString());


        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPass.getText().toString().trim();
        final String password_confirmation = editTextRetypePass.getText().toString().trim();

        final String phone = editTextPhone.getText().toString().trim();
        final String whatsapp = editTextWhatsApp.getText().toString().trim();
        final String regionId = textViewRegionId.getText().toString().trim();
        final String deliveryCost = editTextDeliveryCost.getText().toString().trim();
        final String minimumCharger = editTextMinimumCharger.getText().toString().trim();

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));

        //creating request body for file
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody password_confirmationBody = RequestBody.create(MediaType.parse("text/plain"), password_confirmation);
        RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody whatsappBody = RequestBody.create(MediaType.parse("text/plain"), whatsapp);
        RequestBody regionIdBody = RequestBody.create(MediaType.parse("text/plain"), regionId);
        RequestBody deliveryCostBody = RequestBody.create(MediaType.parse("text/plain"), deliveryCost);
        RequestBody minimumChargerBody = RequestBody.create(MediaType.parse("text/plain"), minimumCharger);
        RequestBody availabilityBody = RequestBody.create(MediaType.parse("text/plain"), "open");

        RequestBody requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(fileUri)), file);



    /*    final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Registering...");
        pDialog.show();

      ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<RegisterResponse> call = apiInterface.Register(nameBody,
                emailBody,
                passwordBody,
                password_confirmationBody,phoneBody,whatsappBody,regionIdBody,deliveryCostBody,minimumChargerBody,
                availabilityBody,categories,requestFile);

        //creating a call and calling the upload image method
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                pDialog.hide();
                if(response.isSuccessful()){
                    String msg = response.body().getMsg();
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                pDialog.hide();
                new CallToast(getActivity(),t.toString());
            }
        });*/
    }


}
