package com.a480.fernando.hackathon.model;

import java.util.Calendar;

/**
 * Created by Fernando on 20/04/2017.
 */

public class Notification {

    private Calendar time;
    private String message;

    public Notification() { }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "time=" + time +
                ", message='" + message + '\'' +
                '}';
    }
}
