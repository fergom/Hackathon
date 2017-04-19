package com.a480.fernando.hackathon.model;

/**
 * Created by Fernando on 19/04/2017.
 */

public class Event {

    private int year;
    private int month;
    private int day;
    private String dayOfWeek;
    private String title;
    private String hour;
    private boolean separator;

    public Event() { }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public boolean getSeparator() {
        return separator;
    }

    public void setSeparator(boolean separator) {
        this.separator = separator;
    }

    @Override
    public String toString() {
        return "Event{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", title='" + title + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }
}
