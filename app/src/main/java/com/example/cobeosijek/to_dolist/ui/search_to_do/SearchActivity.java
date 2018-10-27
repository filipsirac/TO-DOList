package com.example.cobeosijek.to_dolist.ui.search_to_do;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cobeosijek.to_dolist.R;
import com.example.cobeosijek.to_dolist.common.DialogUtils;
import com.example.cobeosijek.to_dolist.database.DatabaseManager;
import com.example.cobeosijek.to_dolist.models.ToDoModel;
import com.example.cobeosijek.to_dolist.ui.details_to_do.DetailsActivity;
import com.example.cobeosijek.to_dolist.ui.listeners.OnDeleteToDoListener;
import com.example.cobeosijek.to_dolist.ui.listeners.OnItemClickListener;
import com.example.cobeosijek.to_dolist.ui.to_do_list.ToDoListAdapter;

import java.util.List;

import io.realm.Realm;

/**
 * Created by cobeosijek on 08/08/2017.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private ImageView back;
    private EditText search;
    private TextView noSearchResults;
    private RecyclerView recyclerView;
    private ImageView iconSearch;

    private ToDoListAdapter toDoListAdapter;

    public static Intent getLaunchIntent(Context from) {
        return new Intent(from, SearchActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_to_do);

        initUi();
        setListeners();
        setAdapter();
    }

    private void setAdapter() {
        toDoListAdapter = new ToDoListAdapter();
        toDoListAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(toDoListAdapter);
    }

    private void initUi() {
        back = (ImageView) findViewById(R.id.icon_back);
        search = (EditText) findViewById(R.id.search_bar);
        iconSearch = (ImageView) findViewById(R.id.icon_search);
        noSearchResults = (TextView) findViewById(R.id.no_search_results);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void setListeners() {
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        iconSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_back:
                finish();
                break;
            case R.id.icon_search:
                findInRealm();
                break;
        }
    }

    private void findInRealm() {
        String searchText = search.getText().toString();
        DatabaseManager databaseManager = new DatabaseManager(Realm.getDefaultInstance());
        List<ToDoModel> todos = databaseManager.searchToDo(searchText);
        if (todos != null && todos.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            noSearchResults.setVisibility(View.GONE);
            toDoListAdapter.setItems(todos);
        } else {
            recyclerView.setVisibility(View.GONE);
            noSearchResults.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(ToDoModel toDoModel) {
        startActivity(DetailsActivity.getLaunchIntent(this, toDoModel));
    }

    @Override
    public void onItemLongClick(int toDoId) {
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
