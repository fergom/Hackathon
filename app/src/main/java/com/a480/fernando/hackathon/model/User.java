package com.a480.fernando.hackathon.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fernando on 27/03/2017.
 */

public class User implements Parcelable {

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

    public User() { }

    protected User(Parcel in) {
        name = in.readString();
        surname = in.readString();
        country = in.readString();
        state = in.readString();
        city = in.readString();
        postalCode = in.readString();
        phoneNumber = in.readString();
        website = in.readString();
        companyName = in.readString();
        nif = in.readString();
        sector = in.readString();
        position = in.readString();
        department = in.readString();
        fact = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(country);
        dest.writeString(state);
        dest.writeString(city);
        dest.writeString(postalCode);
        dest.writeString(phoneNumber);
        dest.writeString(website);
        dest.writeString(companyName);
        dest.writeString(nif);
        dest.writeString(sector);
        dest.writeString(position);
        dest.writeString(department);
        dest.writeByte((byte) (fact ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public boolean isFact() {
        return fact;
    }

    public void setFact(boolean fact) {
        this.fact = fact;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
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
                '}';
    }
}
