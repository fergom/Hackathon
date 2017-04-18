package com.a480.fernando.hackathon.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Fernando on 10/04/2017.
 */

public class Question {

    private long id;
    private String title;
    private String answer;
    private ArrayList<Comment> comments;
    private HashMap<String, Integer> likes;
    private Calendar time;

    public Question() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public HashMap<String, Integer> getLikes() {
        return likes;
    }

    public void setLikes(HashMap<String, Integer> likes) {
        this.likes = likes;
    }

    public void addLike(String uid) {
        this.likes.put(uid, 1);
    }

    public void removeLike(String uid) {
        this.likes.remove(uid);
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

}
