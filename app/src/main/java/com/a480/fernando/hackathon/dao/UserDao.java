package com.a480.fernando.hackathon.dao;

import android.util.Log;

import com.a480.fernando.hackathon.CallbackActivity;
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

    public void onAuthenticated(CallbackActivity callback) {
        userRef = myRef.child("/" + auth.getCurrentUser().getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                user.setName(value.get("name").toString());
                user.setSurname(value.get("surname").toString());
                user.setEmail(value.get("email").toString());
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
        userRef.setValue(user);
    }

    public void logout() {
        auth.signOut();
    }

}
