package com.examlpe.com.sofra.ui.fragments.general;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.examlpe.com.sofra.data.rest.ApiClient;
import com.examlpe.com.sofra.data.rest.ApiInterface;
import com.examlpe.com.sofra.helper.CallToast;
import com.examlpe.com.sofra.data.model.general.contact.ContactResponse;
import com.examlpe.com.sofra.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ContactUsTab extends Fragment {

    public String title;
    EditText editUserName,editEmail,editPhone,editDescribe;
    Button buttonSend;


    @SuppressLint("ValidFragment")
    public ContactUsTab(String title) {
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact_us_tab, container, false);

        editUserName =(EditText)rootView.findViewById(R.id.editUserName);
        editEmail =(EditText)rootView.findViewById(R.id.editEmail);
        editPhone =(EditText)rootView.findViewById(R.id.editPhone);
        editDescribe =(EditText)rootView.findViewById(R.id.editDescribe);
        buttonSend =(Button)rootView.findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.equals("complaint")) { getContactUs("complaint"); }
                else if (title.equals("suggestion")) { getContactUs("suggestion"); }
                else if (title.equals("inquiry")) { getContactUs("inquiry"); }
            }
        });




        return rootView;
    }

    private void getContactUs(final String type) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        String name = editUserName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String content = editDescribe.getText().toString().trim();

        Call<ContactResponse> call = apiInterface.ContactUs(type, name, email, phone, content);
        call.enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                //get response values
                if (response.isSuccessful()) {
                    String msg = response.body().getMsg();
                    new CallToast(getActivity(),msg);
                } else {

                }
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {
            }
        });
    }



}
