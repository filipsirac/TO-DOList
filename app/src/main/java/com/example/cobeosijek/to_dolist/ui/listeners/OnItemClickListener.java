package com.example.cobeosijek.to_dolist.ui.listeners;

import com.example.cobeosijek.to_dolist.models.ToDoModel;

/**
 * Created by cobeosijek on 27/07/2017.
 */

public interface OnItemClickListener {

    void onItemClick(ToDoModel toDoModel);
    void onItemLongClick(int toDoId);
}
