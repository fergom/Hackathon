package com.a480.fernando.hackathon.dao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Fernando on 05/05/2017.
 */

public class TokenDao extends Dao {

    private static DatabaseReference myRef = database.getReference("Tokens");
    private HashMap<String, String> tokens;

    public TokenDao() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tokens = (HashMap<String, String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError);
            }
        });
    }

    public void setToken(String token) {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            myRef.child("/" + token).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
        } else {
            myRef.child("/" + token).setValue("-");
        }
    }

}
