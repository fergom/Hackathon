package com.a480.fernando.hackathon.dao;

import android.util.Log;

import com.a480.fernando.hackathon.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Fernando on 29/03/2017.
 */

public class UserDao extends Dao {

    private final DatabaseReference myRef = database.getReference("Users");
    private static DatabaseReference userRef;
    private final static User user = new User();

    public UserDao() { }

    public void login() {

        userRef = myRef.child("/" + auth.getCurrentUser().getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> value = (HashMap<String, String>) dataSnapshot.getValue();
                user.setName(value.get("name"));
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
        userRef.child("name").setValue(user.getName());
    }

    public void logout() {
        auth.signOut();
    }

}
