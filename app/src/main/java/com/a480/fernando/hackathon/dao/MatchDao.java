package com.a480.fernando.hackathon.dao;

import com.a480.fernando.hackathon.model.Match;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Fernando on 25/04/2017.
 */

public class MatchDao extends Dao {

    private final DatabaseReference myRef = database.getReference("Matches");
    private static LinkedList<Match> matches;

    public MatchDao() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                matches = new LinkedList<Match>();
                HashMap<String, HashMap<String, Object>> value = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                HashMap<String, Object> data;
                Match m;
                if(value != null) {
                    for(String id: value.keySet()) {
                        m = new Match();
                        data = value.get(id);
                        m.setId(id);
                        m.setEmail1((String) data.get("email1"));
                        m.setEmail2((String) data.get("email2"));
                        m.setMatch1((boolean) data.get("match1"));
                        m.setMatch2((boolean) data.get("match2"));
                        m.setAnswer1((boolean) data.get("answer1"));
                        m.setAnswer2((boolean) data.get("answer2"));
                        matches.add(m);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    public int getSize() {
        return  matches.size();
    }

    public HashMap<String, String> getUserMatches(String email) {
        HashMap<String, String> userMatches = new HashMap<String, String>();
        for(Match match: matches) {
            if(match.getEmail1().equals(email) && match.getMatch1() && match.getMatch2()) {
                userMatches.put(match.getEmail2(), match.getId());
            } else if(match.getEmail2().equals(email) && match.getMatch1() && match.getMatch2()) {
                userMatches.put(match.getEmail1(), match.getId());
            }
        }
        return userMatches;
    }

    public Match getMatch(String email1, String email2) {
        for(Match match: matches) {
            if((match.getEmail1().equals(email1) || match.getEmail2().equals(email1)) && (match.getEmail1().equals(email2) || match.getEmail2().equals(email2))){
                return match;
            }
        }
        Match match = new Match();
        match.setEmail1(email1);
        match.setEmail2(email2);
        DatabaseReference r = myRef.push();
        r.setValue(match);
        match.setId(r.getKey());
        return match;
    }

    public void saveMatch(Match match) {
        String id = match.getId();
        match.setId(null);
        myRef.child("/" + id).setValue(match);
    }

}
