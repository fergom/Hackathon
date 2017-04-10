package com.a480.fernando.hackathon.model;

import java.util.Calendar;

/**
 * Created by Fernando on 10/04/2017.
 */

public class Comment {

    private String name;
    private String comment;
    private Calendar time;

    public Comment() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
