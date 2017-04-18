package com.a480.fernando.hackathon.dao;

import android.util.Log;

import com.a480.fernando.hackathon.model.BreakingNew;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Fernando on 18/04/2017.
 */

public class NewsDao extends Dao {

    private final DatabaseReference myRef = database.getReference("News");
    private static ArrayList<BreakingNew> news;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public NewsDao() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                news = new ArrayList<BreakingNew>();

                HashMap<String, Object> values = (HashMap<String, Object>) dataSnapshot.getValue();
                HashMap<String, Object> info;
                BreakingNew n;
                Calendar time = null;
                for(String title: values.keySet()) {
                    n = new BreakingNew();
                    n.setTitle(title);
                    info = (HashMap<String, Object>) values.get(title);
                    n.setInfo((String) info.get("info"));
                    n.setImage((String) info.get("image"));
                    try {
                        time = Calendar.getInstance();
                        time.setTime(sdf.parse(info.get("time").toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    n.setTime(time);
                    news.add(n);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

    public ArrayList<BreakingNew> getNews() {
        return news;
    }

}
