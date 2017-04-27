package com.a480.fernando.hackathon.dao;

import android.util.Log;

import com.a480.fernando.hackathon.ICallbackActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Fernando on 07/04/2017.
 */

public class InfoDao extends Dao {

    private final DatabaseReference myRef = database.getReference("Information");
    private static HashMap<String, String> information;
    private static ICallbackActivity callbackActivity;

    public InfoDao() { }

    public void listenInfo(ICallbackActivity callbackActivity) {
        this.callbackActivity = callbackActivity;

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                information = (HashMap<String, String>) dataSnapshot.getValue();
                callbackActivity.onDataLoaded();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

    public String getLegalInfo() {
        if(information != null){
            return information.get("legal");
        }
        return "";
    }

    public String getHomeInfo() {
        if(information != null){
            return information.get("home");
        }
        return "";
    }

    public String getInscriptionInfo() {
        if(information != null){
            return information.get("inscription");
        }
        return "";
    }

}
