package com.example.cobeosijek.to_dolist.database;

import com.example.cobeosijek.to_dolist.models.ToDoModel;

import java.util.List;

import io.realm.Realm;

/**
 * Created by cobeosijek on 08/08/2017.
 */

public class DatabaseManager {

    private Realm realm;

    public DatabaseManager(Realm realm) {
        this.realm = realm;
    }

    public List<ToDoModel> getAllToDo() {
        return realm.copyFromRealm(realm.where(ToDoModel.class).findAll());
    }

    public void addToDo(ToDoModel toDoModel) {
        int id;
        if (realm.where(ToDoModel.class).count() == 0) {
            id = 0;
        } else {
            id = (realm.where(ToDoModel.class).max("id").intValue() + 1);
        }
        toDoModel.setId(id);
        realm.beginTransaction();
        realm.copyToRealm(toDoModel);
        realm.commitTransaction();
    }

    public void editToDo(ToDoModel toDoModel) {
        if (toDoModel != null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(toDoModel);
            realm.commitTransaction();
        }
    }

    public void deleteToDo(int toDoId) {
        realm.beginTransaction();
        ToDoModel toDoModel = realm.where(ToDoModel.class).equalTo("id", toDoId).findFirst();
        if (toDoModel != null) {
            toDoModel.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    public List<ToDoModel> searchToDo(String searchText) {
        return realm.copyFromRealm(realm.where(ToDoModel.class).contains("title", searchText).findAll());
    }
}
