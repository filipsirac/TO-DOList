package com.example.cobeosijek.to_dolist.ui.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.example.cobeosijek.to_dolist.common.Constants;
import com.example.cobeosijek.to_dolist.ui.listeners.OnDatePickListener;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by cobeosijek on 27/07/2017.
 */

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnDatePickListener onDatePickListener;

    public static DatePickerDialogFragment newInstance(long timestamp) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.DATE_FORMAT, timestamp);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnDatePickListener(OnDatePickListener onDatePickListener) {
        this.onDatePickListener = onDatePickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle extras = getArguments();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(extras.getLong(Constants.DATE_FORMAT)));

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        if (onDatePickListener != null) {
            onDatePickListener.onDatePick(year, month, day);
        }
    }
}
