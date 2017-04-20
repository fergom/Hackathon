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
    private String startTime;
    private String endTime;
    private String info;
    private String address;
    private String image;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", info='" + info + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", separator=" + separator +
                '}';
    }
}
