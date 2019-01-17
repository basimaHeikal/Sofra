package com.examlpe.com.sofra.ui.fragments.general;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.examlpe.com.sofra.helper.LoginMethod;
import com.examlpe.com.sofra.helper.SetCurrentFragment;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.R;


import static com.examlpe.com.sofra.ui.activities.FoodOrderActivity.TAG_CLIENT_SIGN;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate
                (R.layout.fragment_login, container, false);

        Button buttonLogin = (Button)rootView.findViewById(R.id.buttonLogin);
        final EditText editTextEmail =(EditText)rootView.findViewById(R.id.editTextEmail);
        final EditText editTextPassword = (EditText)rootView.findViewById(R.id.editTextPassword);

        //get orderSell key to check it is a client or restaurant
        final String orderSell = SharedPrefManager.getInstance(getActivity()).getKeyOrderSell();

        //on click listener for login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get email and password
                final String email = editTextEmail.getText().toString();
                final String password = editTextPassword.getText().toString();
                //check client or restaurant
                if (orderSell.equals("ORDER") ){
                    //if it is a client
                    ////get login key to check if user opened login fragment from settings icon or from cart icon
                    String login = SharedPrefManager.getInstance(getActivity()).getKeyLogin();
                    if (login.equals("CONFIRM_ORDER") ){
                        LoginMethod.getInstance(getActivity()).login(email,password,0,1,getActivity());
                    }else if(login.equals("SETTINGS")){
                        LoginMethod.getInstance(getActivity()).login(email,password,0,0,getActivity());
                    }else if(login.equals("ALARMS")){
                        LoginMethod.getInstance(getActivity()).login(email,password,0,2,getActivity());
                    }
                }else if(orderSell.equals("SELL")){
                    //if it is a restaurant
                    ////get login key to check if user opened login fragment from settings icon or from cart icon
                    String login = SharedPrefManager.getInstance(getActivity()).getKeyLogin();
                    if (login.equals("SETTINGS") ){
                        LoginMethod.getInstance(getActivity()).login(email,password,1,0,getActivity());
                    }else if(login.equals("ALARMS")){
                        LoginMethod.getInstance(getActivity()).login(email,password,1,2,getActivity());
                    }else{
                        LoginMethod.getInstance(getActivity()).login(email,password,1,0,getActivity());
                    }
                }


            }
        });
        //on click listener for sign up button
        Button buttonClientSign = (Button)rootView.findViewById(R.id.buttonClientSign);
        buttonClientSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check client or restaurant
                if (orderSell.equals("ORDER") ){
                    //if it is a client
                    //start client sign up fragment
                    SetCurrentFragment.getInstance(getActivity()).setCurrentFragment(getActivity(), TAG_CLIENT_SIGN, 12);


                }else if(orderSell.equals("SELL")){
                    //if it is a restaurant
                    SetCurrentFragment.getInstance(getActivity()).setCurrentFragment(getActivity(), TAG_CLIENT_SIGN, 13);

                }


            }
        });

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("تسجيل الدخول");
    }
}
