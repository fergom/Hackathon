package com.a480.fernando.hackathon.dao;

import com.a480.fernando.hackathon.CallbackActivity;
import com.a480.fernando.hackathon.model.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Fernando on 20/04/2017.
 */

public class NotificationsDao extends Dao {

    private final DatabaseReference publicRef = database.getReference("Notifications");
    private DatabaseReference userRef = database.getReference("Users");
    private static ArrayList<Notification> userNotifications, publicNotifications, allNotifications;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private CallbackActivity callbackActivity;
    private boolean publicLoaded, userLoaded;

    public NotificationsDao() { }

    public void listenPublicNotifications(CallbackActivity callbackActivity) {

        this.callbackActivity = callbackActivity;

        publicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                publicLoaded = false;
                publicNotifications = new ArrayList<Notification>();
                HashMap<String, String> values = (HashMap<String, String>) dataSnapshot.getValue();
                Notification notification;
                Calendar time = null;

                for(String n: values.keySet()) {
                    notification = new Notification();
                    notification.setMessage(n);
                    try {
                        time = Calendar.getInstance();
                        time.setTime(sdf.parse(values.get(n).toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    notification.setTime(time);
                    publicNotifications.add(notification);
                }
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    publicLoaded = true;
                    mergeNotifications();
                } else {
                    callbackActivity.onDataLoaded();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void listenUserNotifications(CallbackActivity callbackActivity) {

        this.callbackActivity = callbackActivity;

        this.userRef = userRef.child("/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Notifications");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userLoaded = true;
                userNotifications = new ArrayList<Notification>();
                HashMap<String, String> values = (HashMap<String, String>) dataSnapshot.getValue();
                Notification notification;
                Calendar time = null;

                if(values != null) {
                    for(String n: values.keySet()) {
                        notification = new Notification();
                        notification.setMessage(n);
                        try {
                            time = Calendar.getInstance();
                            time.setTime(sdf.parse(values.get(n).toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        notification.setTime(time);
                        userNotifications.add(notification);
                    }
                }

                userLoaded = true;
                mergeNotifications();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void addNotification(String uid, Notification notification) {
        DatabaseReference ref = database.getReference("Users").child("/" + uid + "/Notifications");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(notification.getMessage(), formatDate(notification.getTime()));
        ref.setValue(map);
    }

    public ArrayList<Notification> getPublicNotifications() {
        return publicNotifications;
    }

    public ArrayList<Notification> getUserNotifications() {
        return allNotifications;
    }

    private void mergeNotifications() {
        if(publicLoaded && userLoaded) {
            allNotifications = new ArrayList<Notification>();
            allNotifications.addAll(userNotifications);
            allNotifications.addAll(publicNotifications);
            callbackActivity.onDataLoaded();
        }
    }

}
