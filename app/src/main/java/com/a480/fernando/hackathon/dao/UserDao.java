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
import java.util.LinkedList;

/**
 * Created by Fernando on 29/03/2017.
 */

public class UserDao extends Dao {

    private final DatabaseReference myRef = database.getReference("Users");
    private static DatabaseReference userRef;
    private final static User user = new User();
    private static LinkedList<User> users;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public UserDao() { }

    public void onAuthenticated(CallbackActivity callback) {
        userRef = myRef.child("/" + auth.getCurrentUser().getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new LinkedList<User>();
                HashMap<String, HashMap<String, Object>> value = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                HashMap<String, Object> data;
                User u;
                for(String uid: value.keySet()) {
                    u = new User();
                    data = value.get(uid);
                    u.setUid(uid);
                    u.setImage((String) data.get("image"));
                    u.setName((String) data.get("name"));
                    u.setSurname((String) data.get("surname"));
                    u.setState((String) data.get("state"));
                    u.setCity((String) data.get("city"));
                    u.setEmail((String) data.get("email"));
                    users.add(u);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                try{
                    user.setUid(auth.getCurrentUser().getUid());
                    Calendar time = null;
                    try {
                        time = Calendar.getInstance();
                        time.setTime(sdf.parse(value.get("lastConnection").toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    user.setLastConnection(time);
                    user.setName((String) value.get("name"));
                    user.setSurname((String) value.get("surname"));
                    user.setEmail((String) value.get("email"));
                    user.setPassword((String) value.get("password"));
                    user.setCountry((String) value.get("country"));
                    user.setState((String) value.get("state"));
                    user.setCity((String) value.get("city"));
                    user.setPostalCode((String) value.get("postalCode"));
                    user.setPhoneNumber((String) value.get("phoneNumber"));
                    user.setWebsite((String) value.get("website"));
                    user.setCompanyName((String) value.get("companyName"));
                    user.setNif((String) value.get("nif"));
                    user.setSector((String) value.get("sector"));
                    user.setPosition((String) value.get("position"));
                    user.setDepartment((String) value.get("department"));
                    user.setFact((boolean) value.get("fact"));
                    user.setImage((String) value.get("image"));
                    user.setSnooze((boolean) value.get("snooze"));
                    user.setNetworking((boolean) value.get("networking"));

                    HashMap<String, String> notifications = (HashMap<String, String>) value.get("Notifications");
                    ArrayList<Notification> notif = new ArrayList<Notification>();
                    Notification notification;

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
                    if(callback != null) {
                        callback.onDataLoaded();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
        if(notifications != null) {
            for(Notification n: notifications) {
                map = new HashMap<String, String>();
                map.put(n.getMessage(), formatDate(n.getTime()));
                notificationsRef.setValue(map);
            }
        }
    }

    public void logout() {
        userRef.child("lastConnection").setValue(formatDate(Calendar.getInstance()));
        auth.signOut();
    }

    public User getRandomUser(String email) {
        int range = (users.size()-1) + 1;
        int random = (int)(Math.random() * range);
        for(int i = 0; i < users.size()*2; i++) {
            if(!users.get(random).getEmail().equals(email)) {
                return users.get(random);
            }
            random = (int)(Math.random() * range);
        }
        return null;
    }

}
