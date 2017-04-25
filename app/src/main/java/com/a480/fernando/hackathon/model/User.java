package com.a480.fernando.hackathon.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Fernando on 27/03/2017.
 */

public class User {

    private String uid;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String country;
    private String state;
    private String city;
    private String postalCode;
    private String phoneNumber;
    private String website;
    private String companyName;
    private String nif;
    private String sector;
    private String position;
    private String department;
    private boolean fact;
    private String image;
    private boolean snooze;
    private boolean networking;
    private ArrayList<Notification> notifications;
    private Calendar lastConnection;

    public User() { }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean getFact() {
        return fact;
    }

    public void setFact(boolean fact) {
        this.fact = fact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getSnooze() {
        return snooze;
    }

    public void setSnooze(boolean snooze) {
        this.snooze = snooze;
    }

    public boolean getNetworking() {
        return networking;
    }

    public void setNetworking(boolean networking) {
        this.networking = networking;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public Calendar getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Calendar lastConnection) {
        this.lastConnection = lastConnection;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", website='" + website + '\'' +
                ", companyName='" + companyName + '\'' +
                ", nif='" + nif + '\'' +
                ", sector='" + sector + '\'' +
                ", position='" + position + '\'' +
                ", department='" + department + '\'' +
                ", fact=" + fact +
                ", image='" + image + '\'' +
                ", snooze=" + snooze +
                ", networking=" + networking +
                '}';
    }
}
