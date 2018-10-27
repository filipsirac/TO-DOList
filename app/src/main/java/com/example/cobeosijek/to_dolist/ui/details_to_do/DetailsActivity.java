package com.example.cobeosijek.to_dolist.ui.details_to_do;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cobeosijek.to_dolist.R;
import com.example.cobeosijek.to_dolist.common.Constants;
import com.example.cobeosijek.to_dolist.common.DateTimeUtils;
import com.example.cobeosijek.to_dolist.models.ToDoModel;
import com.example.cobeosijek.to_dolist.ui.edit_to_do.EditToDoActivity;

import java.util.Locale;

/**
 * Created by cobeosijek on 28/07/2017.
 */

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView edit;
    private ImageView back;
    private TextView title;
    private TextView description;
    private TextView date;
    private TextView time;
    private ToDoModel toDoModel;

    public static Intent getLaunchIntent(Context from, ToDoModel toDoModel) {
        Intent intent = new Intent(from, DetailsActivity.class);
        intent.putExtra(Constants.TO_DO_MODEL, toDoModel);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initUi();
        getExtras();
        setListeners();
    }

    private void initUi() {
        edit = (ImageView) findViewById(R.id.icon_edit);
        back = (ImageView) findViewById(R.id.icon_back);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
    }

    private void getExtras() {
        Intent intent = getIntent();
        toDoModel = (ToDoModel) intent.getSerializableExtra(Constants.TO_DO_MODEL);
        if (toDoModel != null) {
            setData();
        }
    }

    private void setData() {
        description.setText(toDoModel.getDescription());
        title.setText(toDoModel.getTitle());
        date.setText(DateTimeUtils.getDateFormat(toDoModel.getDate()));
        time.setText(String.format(Locale.getDefault(), getString(R.string.time_format), toDoModel.getHours(), toDoModel.getMinutes()));
    }

    private void setListeners() {
        edit.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_edit:
                Intent intent = EditToDoActivity.getLaunchIntent(this, toDoModel);
                startActivityForResult(intent, Constants.EDIT_TO_DO);
                break;
            case R.id.icon_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.EDIT_TO_DO) {
            if (data != null && data.getSerializableExtra(Constants.TO_DO_MODEL) != null) {
                toDoModel = (ToDoModel) data.getSerializableExtra(Constants.TO_DO_MODEL);
                setData();
            }
        }
    }
}
