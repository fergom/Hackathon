package com.a480.fernando.hackathon.model;

/**
 * Created by Fernando on 25/04/2017.
 */

public class Match {

    private String id;
    private String uid1;
    private String uid2;
    private boolean match1;
    private boolean match2;
    private boolean answer1;
    private boolean answer2;

    public Match() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid1() {
        return uid1;
    }

    public void setUid1(String uid1) {
        this.uid1 = uid1;
    }

    public String getUid2() {
        return uid2;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
    }

    public boolean getMatch1() {
        return match1;
    }

    public void setMatch1(boolean match1) {
        this.match1 = match1;
    }

    public boolean getMatch2() {
        return match2;
    }

    public void setMatch2(boolean match2) {
        this.match2 = match2;
    }

    public boolean getAnswer1() {
        return answer1;
    }

    public void setAnswer1(boolean answer1) {
        this.answer1 = answer1;
    }

    public boolean getAnswer2() {
        return answer2;
    }

    public void setAnswer2(boolean answer2) {
        this.answer2 = answer2;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                ", uid1='" + uid1 + '\'' +
                ", uid2='" + uid2 + '\'' +
                ", match1=" + match1 +
                ", match2=" + match2 +
                ", answer1=" + answer1 +
                ", answer2=" + answer2 +
                '}';
    }
}
