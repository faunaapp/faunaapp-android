package com.example.faunaapp.DTO;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "entry_table")
public class Entry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String heading, title, description, date, time, token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entry() {
    }

    @Ignore
    public Entry(String heading, String title, String description, String date, String time, String token) {
        this(heading, title, description, date, time);
        this.token = token;
    }

    @Ignore
    public Entry(String heading, String title, String description, String date, String time)
    {
        this(heading, title, date, time);
        this.description = description;
    }
    @Ignore
    public Entry(String heading, String title,  String date, String time)
    {
        this.heading = heading;
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeading() {
        return heading;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

}
