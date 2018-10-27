package com.example.cobeosijek.to_dolist.ui.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import com.example.cobeosijek.to_dolist.common.Constants;
import com.example.cobeosijek.to_dolist.ui.listeners.OnTimePickListener;

import java.util.Calendar;

/**
 * Created by cobeosijek on 27/07/2017.
 */

public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private OnTimePickListener onTimePickListener;

    public static TimePickerDialogFragment newInstance(int hours, int minutes) {
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TIME_HOURS, hours);
        bundle.putInt(Constants.TIME_MINUTES, minutes);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnTimePickListener(OnTimePickListener onTimePickListener) {
        this.onTimePickListener = onTimePickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle extras = getArguments();
        Calendar c = Calendar.getInstance();
        int hours = extras.getInt(Constants.TIME_HOURS);
        int minutes = extras.getInt(Constants.TIME_MINUTES);
        c.set(0, 0, 0, hours, minutes);
        return new TimePickerDialog(getContext(), this, hours, minutes, true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        if (onTimePickListener != null) {
            onTimePickListener.onTimePick(hours, minutes);
        }
    }
}
