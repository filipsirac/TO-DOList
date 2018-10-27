package com.example.cobeosijek.to_dolist.ui.to_do_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.cobeosijek.to_dolist.R;
import com.example.cobeosijek.to_dolist.common.DialogUtils;
import com.example.cobeosijek.to_dolist.database.DatabaseManager;
import com.example.cobeosijek.to_dolist.models.ToDoModel;
import com.example.cobeosijek.to_dolist.ui.add_to_do.AddToDoActivity;
import com.example.cobeosijek.to_dolist.ui.details_to_do.DetailsActivity;
import com.example.cobeosijek.to_dolist.ui.listeners.OnDeleteToDoListener;
import com.example.cobeosijek.to_dolist.ui.listeners.OnItemClickListener;
import com.example.cobeosijek.to_dolist.ui.search_to_do.SearchActivity;

import java.util.List;

import io.realm.Realm;

public class ToDoListActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private RecyclerView recyclerView;
    private ToDoListAdapter toDoListAdapter;
    private FloatingActionButton addButton;
    private ImageView searchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        intiUi();
        setListeners();
        setAdapter();
    }

    private void intiUi() {
        addButton = (FloatingActionButton) findViewById(R.id.icon_add);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        searchIcon = (ImageView) findViewById(R.id.icon_search);
    }

    private void setListeners() {
        addButton.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
    }

    private void setAdapter() {
        toDoListAdapter = new ToDoListAdapter();
        toDoListAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(toDoListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseManager databaseManager = new DatabaseManager(Realm.getDefaultInstance());
        List<ToDoModel> articles = databaseManager.getAllToDo();
        toDoListAdapter.setItems(articles);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_add:
                startActivity(AddToDoActivity.getLaunchIntent(this));
                break;
            case R.id.icon_search:
                startActivity(SearchActivity.getLaunchIntent(this));
                break;
        }
    }

    @Override
    public void onItemClick(ToDoModel toDoModel) {
        startActivity(DetailsActivity.getLaunchIntent(this, toDoModel));
    }

    @Override
    public void onItemLongClick(final int toDoId) {
        DialogUtils.showDeleteDialog(this, toDoId, getDeleteListener());
    }

    private OnDeleteToDoListener getDeleteListener() {
        return new OnDeleteToDoListener() {
            @Override
            public void onDeleteToDo(int id) {
                DatabaseManager databaseManager = new DatabaseManager(Realm.getDefaultInstance());
                databaseManager.deleteToDo(id);
                toDoListAdapter.removeToDo(id);
            }
        };
    }
}
