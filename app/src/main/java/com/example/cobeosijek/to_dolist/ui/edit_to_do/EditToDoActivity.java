package com.example.cobeosijek.to_dolist.ui.edit_to_do;

import android.app.Activity;
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

import com.example.cobeosijek.to_dolist.R;
import com.example.cobeosijek.to_dolist.common.Constants;
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
 * Created by cobeosijek on 28/07/2017.
 */

public class EditToDoActivity extends AppCompatActivity implements View.OnClickListener, OnDatePickListener, OnTimePickListener {

    private ImageView back;
    private EditText title;
    private EditText description;
    private TextView date;
    private TextView time;
    private View dateLabel;
    private View timeLabel;
    private Button saveChanges;

    private ToDoModel toDoModel;

    public static Intent getLaunchIntent(Context from, ToDoModel toDoModel) {
        Intent intent = new Intent(from, EditToDoActivity.class);
        intent.putExtra(Constants.TO_DO_MODEL, toDoModel);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do);

        initUi();
        setListeners();
        getExtras();
    }

    private void initUi() {
        back = (ImageView) findViewById(R.id.icon_back);
        title = (EditText) findViewById(R.id.title_edit);
        description = (EditText) findViewById(R.id.description_edit);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        dateLabel = findViewById(R.id.date_label);
        timeLabel = findViewById(R.id.time_label);
        saveChanges = (Button) findViewById(R.id.save_changes);
    }

    private void setListeners() {
        back.setOnClickListener(this);
        dateLabel.setOnClickListener(this);
        timeLabel.setOnClickListener(this);
        saveChanges.setOnClickListener(this);
    }

    private void getExtras() {
        Intent intent = getIntent();

        toDoModel = (ToDoModel) intent.getSerializableExtra(Constants.TO_DO_MODEL);

        if (toDoModel != null) {
            setData(toDoModel);
        }
    }

    private void showDatePicker(long timestamp) {
        DatePickerDialogFragment dialogFragment = DatePickerDialogFragment.newInstance(timestamp);
        dialogFragment.setOnDatePickListener(this);
        dialogFragment.show(getSupportFragmentManager(), Constants.DATE_PICKER);
    }

    private void showTimePicker() {
        TimePickerDialogFragment dialogFragment = TimePickerDialogFragment.newInstance(toDoModel.getHours(), toDoModel.getMinutes());
        dialogFragment.setOnTimePickListener(this);
        dialogFragment.show(getSupportFragmentManager(), Constants.TIME_PICKER);
    }

    private void setData(ToDoModel toDoModel) {
        description.setText(toDoModel.getDescription());
        title.setText(toDoModel.getTitle());
        date.setText(DateTimeUtils.getDateFormat(toDoModel.getDate()));
        time.setText(String.format(Locale.getDefault(), getString(R.string.time_format), toDoModel.getHours(), toDoModel.getMinutes()));
    }

    @Override
    public void onDatePick(int year, int month, int day) {
        toDoModel.setDate(DateTimeUtils.getDateInMilliseconds(year, month, day));
        date.setText(String.format(Locale.getDefault(), getString(R.string.date_format), day, month + 1, year));
    }

    @Override
    public void onTimePick(int hours, int minutes) {
        toDoModel.setHours(hours);
        toDoModel.setMinutes(minutes);
        time.setText(String.format(Locale.getDefault(), getString(R.string.time_format), hours, minutes));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_back:
                finish();
                break;
            case R.id.save_changes:
                toDoModel.setTitle(title.getText().toString());
                toDoModel.setDescription(description.getText().toString());
                DatabaseManager databaseManager = new DatabaseManager(Realm.getDefaultInstance());
                databaseManager.editToDo(toDoModel);
                goBackUpdateToDo();
                break;
            case R.id.date_label:
                if (toDoModel != null) {
                    showDatePicker(toDoModel.getDate());
                }
                break;
            case R.id.time_label:
                if (toDoModel != null) {
                    showTimePicker();
                }
        }
    }

    private void goBackUpdateToDo() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.TO_DO_MODEL, toDoModel);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
