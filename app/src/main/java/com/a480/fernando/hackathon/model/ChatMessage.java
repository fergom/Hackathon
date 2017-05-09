package com.a480.fernando.hackathon.model;

import java.util.Calendar;

/**
 * Created by Fernando on 08/05/2017.
 */

public class ChatMessage {

    private String senderUid;
    private String message;
    private Calendar time;
    private boolean firstOfDay;

    public ChatMessage() {}

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public boolean isFirstOfDay() {
        return firstOfDay;
    }

    public void setFirstOfDay(boolean firstOfDay) {
        this.firstOfDay = firstOfDay;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "senderUid='" + senderUid + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
