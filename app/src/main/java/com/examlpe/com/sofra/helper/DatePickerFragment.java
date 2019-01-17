package com.examlpe.com.sofra.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.examlpe.com.sofra.R;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
        int month1=month+1;

        String dialog = SharedPrefManager.getInstance(getActivity()).getKEY_DIALOG();

        if (dialog.equals("FROM") ){
            TextView tv = (TextView) getActivity().findViewById(R.id.textViewStarting);
            String stringOfDate = year + "-" + month1 + "-" + day;
            tv.setText(stringOfDate);
        }else if (dialog.equals("TO") ){
            TextView tv = (TextView) getActivity().findViewById(R.id.textViewEnding);
            String stringOfDate = year + "-" + month1 + "-" + day;
            tv.setText(stringOfDate);
        }

    }
}