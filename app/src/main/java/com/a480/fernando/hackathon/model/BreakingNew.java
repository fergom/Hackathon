package com.a480.fernando.hackathon.model;

import java.util.Calendar;

/**
 * Created by Fernando on 18/04/2017.
 */

public class BreakingNew {

    private String title;
    private String image;
    private String info;
    private Calendar time;

    public BreakingNew() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BreakingNew{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", info='" + info + '\'' +
                ", time=" + time +
                '}';
    }
}
