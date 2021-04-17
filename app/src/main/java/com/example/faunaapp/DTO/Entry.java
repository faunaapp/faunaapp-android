package com.example.faunaapp.DTO;

public class Entry {
    private String heading, title, description, date, time;

    public Entry(String heading, String title, String description, String date, String time) {
        this.heading = heading;
        this.title = title;
        this.description = description;
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
