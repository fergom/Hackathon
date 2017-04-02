package com.a480.fernando.hackathon.model;

/**
 * Created by Fernando on 29/03/2017.
 */

public class Service {

    private String name;
    private String address;
    private String phone;
    private String type;
    private double latitude;
    private double longitude;

    public Service() { }

    public Service(String name, String address, String phone, String type, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
