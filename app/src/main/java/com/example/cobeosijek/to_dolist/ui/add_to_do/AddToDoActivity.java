package com.example.cobeosijek.to_dolist.ui.add_to_do;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cobeosijek.to_dolist.R;
import com.example.cobeosijek.to_dolist.common.Constants;
import com.example.cobeosijek.to_dolist.common.DataUtils;
import com.example.cobeosijek.to_dolist.common.DateTimeUtils;
import com.example.cobeosijek.to_dolist.database.DatabaseManager;
import com.example.cobeosijek.to_dolist.models.ToDoModel;
import com.example.cobeosijek.to_dolist.ui.dialog.DatePickerDialogFragment;
import com.example.cobeosijek.to_dolist.ui.dialog.TimePickerDialogFragment;
import com.example.cobeosijek.to_dolist.ui.listeners.OnDatePickListener;
import com.example.cobeosijek.to_dolist.ui.listeners.OnTimePickListener;

import java.util.Locale;

import io.realm.Realm;

/**
 * Created by cobeosijek on 27/07/2017.
 */

public class AddToDoActivity extends AppCompatActivity implements View.OnClickListener, OnDatePickListener, OnTimePickListener {

    private TextView dateLabel;
    private TextView timeLabel;
    private TextView date;
    private TextView time;
    private EditText title;
    private EditText description;
    private Button save;
    private ImageView back;

    private long dateInMilliseconds;
    private int hours;
    private int minutes;

    public static Intent getLaunchIntent(Context from) {
        return new Intent(from, AddToDoActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        prepareUi();
        setListeners();
        setDefaultDate();
    }

    private void prepareUi() {
        dateLabel = (TextView) findViewById(R.id.date_label);
        timeLabel = (TextView) findViewById(R.id.time_label);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        title = (EditText) findViewById(R.id.title_edit);
        description = (EditText) findViewById(R.id.description_edit);
        save = (Button) findViewById(R.id.save);
        back = (ImageView) findViewById(R.id.icon_back);
    }

    private void setListeners() {
        dateLabel.setOnClickListener(this);
        timeLabel.setOnClickListener(this);
        save.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void setDefaultDate() {
        dateInMilliseconds = DateTimeUtils.getCurrentDate();
        date.setText(DateTimeUtils.getDateFormat(dateInMilliseconds));
    }

    private void showDatePicker() {
        DatePickerDialogFragment dialogFragment = DatePickerDialogFragment.newInstance(System.currentTimeMillis());
        dialogFragment.setOnDatePickListener(this);
        dialogFragment.show(getSupportFragmentManager(), Constants.DATE_PICKER);
    }

    private void showTimePicker() {
        TimePickerDialogFragment dialogFragment = TimePickerDialogFragment.newInstance(hours, minutes);
        dialogFragment.setOnTimePickListener(this);
        dialogFragment.show(getSupportFragmentManager(), Constants.TIME_PICKER);
    }

    @Override
    public void onDatePick(int year, int month, int day) {
        dateInMilliseconds = DateTimeUtils.getDateInMilliseconds(year, month, day);
        date.setText(String.format(Locale.getDefault(), getString(R.string.date_format), day, month + 1, year));
    }

    @Override
    public void onTimePick(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
        time.setText(String.format(Locale.getDefault(), getString(R.string.time_format), hours, minutes));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_label:
                showDatePicker();
                break;
            case R.id.time_label:
                showTimePicker();
                break;
            case R.id.save:
                checkError();
                break;
            case R.id.icon_back:
                finish();
                break;
        }
    }

    private void checkError() {
        String titleText = title.getText().toString();
        String descriptionText = description.getText().toString();

        if (titleText.isEmpty() || descriptionText.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_toast),
                    Toast.LENGTH_SHORT).show();
        } else {
            ToDoModel toDoModel = DataUtils.getArticleModel(titleText, descriptionText, dateInMilliseconds, hours, minutes);
            DatabaseManager databaseManager = new DatabaseManager(Realm.getDefaultInstance());
            databaseManager.addToDo(toDoModel);
            finish();
        }
    }


}
