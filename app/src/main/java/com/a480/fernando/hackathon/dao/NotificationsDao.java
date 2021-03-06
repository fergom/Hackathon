package com.a480.fernando.hackathon.dao;

import com.a480.fernando.hackathon.ICallbackActivity;
import com.a480.fernando.hackathon.model.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Fernando on 20/04/2017.
 */

public class NotificationsDao extends Dao {

    private final DatabaseReference publicRef = database.getReference("Notifications");
    private DatabaseReference userRef = database.getReference("NotificationsUser");
    private static ArrayList<Notification> userNotifications, publicNotifications, allNotifications;
    private ICallbackActivity callbackActivity;
    private boolean publicLoaded, userLoaded;
    private String uid;

    public NotificationsDao() { }

    public void setCallbackActivity(ICallbackActivity callbackActivity) {
        this.callbackActivity = callbackActivity;
    }

    public void listenPublicNotifications() {

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

                Collections.sort(publicNotifications, new Comparator<Notification>() {
                    @Override
                    public int compare(Notification o1, Notification o2) {
                        return o2.getTime().compareTo(o1.getTime());
                    }
                });

                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    publicLoaded = true;
                    mergeNotifications();
                } else {
                    if(callbackActivity != null) {
                        callbackActivity.onDataLoaded();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void listenUserNotifications(String uid) {

        this.uid = uid;
        DatabaseReference notifRef = userRef.child("/" + uid);

        notifRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userLoaded = false;
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
        DatabaseReference ref = userRef.child("/" + uid);
        ref.child(notification.getMessage()).setValue(formatDate(notification.getTime()));
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
            Collections.sort(allNotifications, new Comparator<Notification>() {
                @Override
                public int compare(Notification o1, Notification o2) {
                    return o2.getTime().compareTo(o1.getTime());
                }
            });
            if(callbackActivity != null) {
                callbackActivity.onDataLoaded();
            }
        }
    }

}
