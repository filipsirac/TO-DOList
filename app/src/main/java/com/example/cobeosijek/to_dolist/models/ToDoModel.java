package com.example.cobeosijek.to_dolist.models;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by cobeosijek on 27/07/2017.
 */

public class ToDoModel extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private String title;
    private String description;

    //timestamp
    private long date;

    //time of day
    private int hours;
    private int minutes;

    public ToDoModel(){

    }

    public ToDoModel(int id, String title, String description, long date, int hours, int minutes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.hours=hours;
        this.minutes=minutes;
    }


    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
