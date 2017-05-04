package com.a480.fernando.hackathon.dao;

import android.util.Log;

import com.a480.fernando.hackathon.model.Feedback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Fernando on 07/04/2017.
 */

public class FeedbackDao extends Dao {

    private final static DatabaseReference myRef = database.getReference("Feedback");
    private static DatabaseReference feedbackRef;
    private static long itemsLength;

    public FeedbackDao() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Object> value = (ArrayList<Object>) dataSnapshot.getValue();
                itemsLength = value.size();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

    public void saveFeedback(Feedback feedback) {
        feedbackRef = myRef.child("/" + itemsLength);
        feedbackRef.setValue(feedback);
    }

}
