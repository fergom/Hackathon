package com.a480.fernando.hackathon.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Fernando on 10/04/2017.
 */

public class Question {

    private String title;
    private String answer;
    private ArrayList<Comment> comments;
    private long likes;
    private Calendar time;

    public Question() { }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
