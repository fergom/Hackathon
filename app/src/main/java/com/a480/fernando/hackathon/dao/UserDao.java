package com.a480.fernando.hackathon.dao;

import android.util.Log;

import com.a480.fernando.hackathon.ICallbackActivity;
import com.a480.fernando.hackathon.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fernando on 29/03/2017.
 */

public class UserDao extends Dao {

    private final DatabaseReference myRef = database.getReference("Users");
    private static DatabaseReference userRef;
    private final static User user = new User();
    private static ArrayList<User> users;
    private static ICallbackActivity callback;
    private static ICallbackActivity callbackNotification;
    private static boolean isSnooze = false;

    public UserDao() { }

    public void isSnooze(ICallbackActivity callbackNotification) {
        this.callbackNotification = callbackNotification;

        if(auth.getCurrentUser() != null) {
            myRef.child("/" + auth.getCurrentUser().getUid() + "/snooze").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    isSnooze = (boolean) dataSnapshot.getValue();
                    callbackNotification.onDataLoaded();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("FIREBASE", "Failed to read value.", error.toException());
                }
            });
        } else {
            callbackNotification.onDataLoaded();
        }
    }

    public boolean getSnooze() {
        return isSnooze;
    }

    public void setCallback(ICallbackActivity callback) {
        this.callback = callback;
    }

    public void onAuthenticated() {
        userRef = myRef.child("/" + auth.getCurrentUser().getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new ArrayList<User>();
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
                    u.setPhoneNumber((String) data.get("phoneNumber"));
                    u.setNetworking((boolean) data.get("networking"));
                    u.setSpeaker((boolean) data.get("speaker"));
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
                    if(auth.getCurrentUser() != null) {
                        user.setUid(auth.getCurrentUser().getUid());
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
                        user.setSpeaker((boolean) value.get("speaker"));

                        if(callback != null) {
                            callback.onDataLoaded();
                        }
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

    public ArrayList<User> getUsers(HashMap<String, String> matches) {
        ArrayList<User> filteredUsers = new ArrayList<User>();
        for(User u: users) {
            if(matches.get(u.getUid()) != null) {
                u.setChatId(matches.get(u.getUid()));
                filteredUsers.add(u);
            }
        }
        return filteredUsers;
    }

    public User getUserUid(String uid) {
        for(User u: users) {
            if(u.getUid().equals(uid)) {
                return u;
            }
        }
        return null;
    }

    public void saveUser(User user) {
        user.setChatId(null);
        userRef.setValue(user);
    }

    public void updateProfileImage(String url) {
        userRef.child("image").setValue(url);
    }

    public void logout() {
        auth.signOut();
    }

    public User getRandomUser(String email) {
        int range = (users.size()-1) + 1;
        int random = (int)(Math.random() * range);
        for(int i = 0; i < users.size()*2; i++) {
            if(!users.get(random).getEmail().equals(email) && users.get(random).getNetworking() && !users.get(random).isSpeaker()) {
                return users.get(random);
            }
            random = (int)(Math.random() * range);
        }
        return null;
    }

}
