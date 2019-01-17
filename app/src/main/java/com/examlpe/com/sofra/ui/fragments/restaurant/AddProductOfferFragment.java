package com.examlpe.com.sofra.ui.fragments.restaurant;



import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.helper.DatePickerFragment;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.data.model.restaurant.AddOffer.AddOfferResponse;
import com.examlpe.com.sofra.data.model.restaurant.AddProduct.AddProductResponse;
import com.examlpe.com.sofra.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AddProductOfferFragment extends Fragment {


    private EditText editProductOfferName,editPrice,editTime,editDescribe;
    private LinearLayout offer_linear_layout;
    private TextView textViewPhoto,textViewEnding,textViewStarting;
    private ImageView imageViewAddPhoto;
    private Button buttonProductOffer;

    Uri selectedImage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_add_product_offer, container, false);

        initElements(rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        String add = SharedPrefManager.getInstance(getActivity()).getKeyAdd();
        if (add.equals("PRODUCT") ){
            getActivity().setTitle("أضف منتج");
        }else if(add.equals("OFFER")){
            getActivity().setTitle("أضف عرض");
        }
    }

    private void initElements(View rootView){
        offer_linear_layout = (LinearLayout) rootView.findViewById(R.id.offer_linear_layout);
        editProductOfferName = (EditText) rootView.findViewById(R.id.editProductOfferName);
        editPrice = (EditText) rootView.findViewById(R.id.editPrice);
        editTime = (EditText) rootView.findViewById(R.id.editTime);
        editDescribe = (EditText) rootView.findViewById(R.id.editDescribe);
        textViewPhoto = (TextView) rootView.findViewById(R.id.textViewPhoto);
        textViewEnding = (TextView) rootView.findViewById(R.id.textViewEnding);
        textViewStarting = (TextView) rootView.findViewById(R.id.textViewStarting);
        imageViewAddPhoto = (ImageView) rootView.findViewById(R.id.imageViewAddPhoto);
        buttonProductOffer = (Button) rootView.findViewById(R.id.buttonProductOffer);

        String add = SharedPrefManager.getInstance(getActivity()).getKeyAdd();

        if (add.equals("PRODUCT") ){
            editProductOfferName.setHint("إسم المنتج");
            textViewPhoto.setText("إضافة صورة المنتج");
            offer_linear_layout.setVisibility(View.GONE);
            //on click listener for imageViewAddPhoto to open gallery and pick up photo
            imageViewAddPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //open gallery to select photo
                    pickPhoto();
                }
            });
          buttonProductOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadProduct(selectedImage);
                }
            });

        }else if(add.equals("OFFER")){
            editProductOfferName.setHint("إسم العرض");
            textViewPhoto.setText("إضافة صورة العرض");
            editTime.setVisibility(View.GONE);
            textViewStarting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPrefManager.getInstance(getActivity()).setKEY_DIALOG("FROM");
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getFragmentManager(),"Date Picker");
                }
            });
            textViewEnding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPrefManager.getInstance(getActivity()).setKEY_DIALOG("TO");
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getFragmentManager(),"Date Picker");
                }
            });
            //on click listener for imageViewAddPhoto to open gallery and pick up photo
            imageViewAddPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //open gallery to select photo
                    pickPhoto();
                }
            });
            buttonProductOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadOffer(selectedImage);
                }
            });
        }

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

//            new CallToast(getActivity(),String.valueOf(selectedImage));

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

    private void uploadProduct(Uri fileUri) {

        final String api_token = SharedPrefManagerOwner.getInstance(getActivity()).getKeyApiToken();

        final String description = editDescribe.getText().toString().trim();
        final String price = editPrice.getText().toString().trim();
        final String preparing_time = editTime.getText().toString().trim();
        final String name = editProductOfferName.getText().toString().trim();


        //creating a file
        File file = new File(getRealPathFromURI(fileUri));

        //creating request body for file
        RequestBody api_tokenBody = RequestBody.create(MediaType.parse("text/plain"), api_token);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody preparing_timeBody = RequestBody.create(MediaType.parse("text/plain"), preparing_time);
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(fileUri)), file);



        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Adding product...");
        pDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<AddProductResponse> call = apiInterface.AddProduct(api_tokenBody,
                descriptionBody,priceBody,preparing_timeBody,nameBody,requestFile);

        //creating a call and calling the upload image method
        call.enqueue(new Callback<AddProductResponse>() {
            @Override
            public void onResponse(Call<AddProductResponse> call, Response<AddProductResponse> response) {
                pDialog.hide();
                if(response.isSuccessful()){
                    String msg = response.body().getMsg();
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<AddProductResponse> call, Throwable t) {
                pDialog.hide();
                new CallToast(getActivity(),t.toString());
            }
        });
    }
    private void uploadOffer(Uri fileUri) {
        final String api_token = SharedPrefManagerOwner.getInstance(getActivity()).getKeyApiToken();

        final String description = editDescribe.getText().toString().trim();
        final String price = editPrice.getText().toString().trim();
        final String name = editProductOfferName.getText().toString().trim();
        final String starting_at = textViewStarting.getText().toString().trim();
        final String ending_at = textViewEnding.getText().toString().trim();

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(fileUri)), file);
        RequestBody api_tokenBody = RequestBody.create(MediaType.parse("text/plain"), api_token);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody starting_ateBody = RequestBody.create(MediaType.parse("text/plain"), starting_at);
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody ending_atBody = RequestBody.create(MediaType.parse("text/plain"), ending_at);




        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Adding Offer...");
        pDialog.show();

        Call<AddOfferResponse> call = apiInterface.AddOffer(api_tokenBody,
                descriptionBody,priceBody,starting_ateBody,nameBody,ending_atBody,requestFile);

        //creating a call and calling the upload image method
        call.enqueue(new Callback<AddOfferResponse>() {
            @Override
            public void onResponse(Call<AddOfferResponse> call, Response<AddOfferResponse> response) {
                pDialog.hide();
                if(response.isSuccessful()){
                    String msg = response.body().getMsg();
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<AddOfferResponse> call, Throwable t) {

            }
        });
    }



}
