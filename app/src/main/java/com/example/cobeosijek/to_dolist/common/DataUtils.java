package com.example.cobeosijek.to_dolist.common;

import com.example.cobeosijek.to_dolist.models.ToDoModel;

/**
 * Created by cobeosijek on 27/07/2017.
 */

public class DataUtils {

    public static ToDoModel getArticleModel(String title, String description, long date, int hours, int minutes) {
        ToDoModel toDoModel = new ToDoModel();
        toDoModel.setTitle(title);
        toDoModel.setDescription(description);
        toDoModel.setDate(date);
        toDoModel.setHours(hours);
        toDoModel.setMinutes(minutes);
        return toDoModel;
    }
}
