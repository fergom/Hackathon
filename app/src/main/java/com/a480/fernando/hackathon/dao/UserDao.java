package com.a480.fernando.hackathon.dao;

import android.util.Log;

import com.a480.fernando.hackathon.CallbackActivity;
import com.a480.fernando.hackathon.model.Notification;
import com.a480.fernando.hackathon.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Fernando on 29/03/2017.
 */

public class UserDao extends Dao {

    private final DatabaseReference myRef = database.getReference("Users");
    private static DatabaseReference userRef;
    private final static User user = new User();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public UserDao() { }

    public void onAuthenticated(CallbackActivity callback) {
        userRef = myRef.child("/" + auth.getCurrentUser().getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                user.setName(value.get("name").toString());
                user.setSurname(value.get("surname").toString());
                user.setEmail(value.get("email").toString());
                user.setPassword(value.get("password").toString());
                user.setCountry(value.get("country").toString());
                user.setState(value.get("state").toString());
                user.setCity(value.get("city").toString());
                user.setPostalCode(value.get("postalCode").toString());
                user.setPhoneNumber(value.get("phoneNumber").toString());
                user.setWebsite(value.get("website").toString());
                user.setCompanyName(value.get("companyName").toString());
                user.setNif(value.get("nif").toString());
                user.setSector(value.get("sector").toString());
                user.setPosition(value.get("position").toString());
                user.setDepartment(value.get("department").toString());
                user.setFact((boolean) value.get("fact"));
                user.setImage(value.get("image").toString());
                user.setSnooze((boolean) value.get("snooze"));
                user.setNetworking((boolean) value.get("networking"));

                HashMap<String, String> notifications = (HashMap<String, String>) value.get("Notifications");
                ArrayList<Notification> notif = new ArrayList<Notification>();
                Notification notification;
                Calendar time = null;

                if(notifications != null) {
                    for(String n: notifications.keySet()) {
                        notification = new Notification();
                        notification.setMessage(n);
                        try {
                            time = Calendar.getInstance();
                            time.setTime(sdf.parse(notifications.get(n).toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        notification.setTime(time);
                        notif.add(notification);
                    }
                }

                user.setNotifications(notif);
                callback.onDataLoaded();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

    public User getUser() {
        return user;
    }

    public void saveUser(User user) {
        ArrayList<Notification> notifications = user.getNotifications();
        user.setNotifications(null);
        userRef.setValue(user);
        DatabaseReference notificationsRef = userRef.child("Notifications");
        HashMap<String, String> map;
        for(Notification n: notifications) {
            map = new HashMap<String, String>();
            map.put(n.getMessage(), formatDate(n.getTime()));
            notificationsRef.setValue(map);
        }
    }

    public void logout() {
        auth.signOut();
    }

    private String formatDate(Calendar date) {
        StringBuilder dateString = new StringBuilder();
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH) + 1;
        int year = date.get(Calendar.YEAR);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        int second = date.get(Calendar.SECOND);
        if(day < 10) {
            dateString.append("0");
        }
        dateString.append(day + "/");
        if(month < 10) {
            dateString.append("0");
        }
        dateString.append(month + "/");
        dateString.append(year + " ");
        if(hour < 10) {
            dateString.append("0");
        }
        dateString.append(hour + ":");
        if(minute < 10) {
            dateString.append("0");
        }
        dateString.append(minute + ":");
        if(second < 10) {
            dateString.append("0");
        }
        dateString.append(second + "");
        return dateString.toString();
    }

}
