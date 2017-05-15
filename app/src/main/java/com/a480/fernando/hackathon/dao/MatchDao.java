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
                        m.setUid1((String) data.get("uid1"));
                        m.setUid2((String) data.get("uid2"));
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

    public HashMap<String, String> getUserMatches(String uid) {
        HashMap<String, String> userMatches = new HashMap<String, String>();
        for(Match match: matches) {
            if(match.getUid1().equals(uid) && match.getMatch1() && match.getMatch2()) {
                userMatches.put(match.getUid2(), match.getId());
            } else if(match.getUid2().equals(uid) && match.getMatch1() && match.getMatch2()) {
                userMatches.put(match.getUid1(), match.getId());
            }
        }
        return userMatches;
    }

    public Match getMatch(String uid1, String uid2) {
        for(Match match: matches) {
            if((match.getUid1().equals(uid1) || match.getUid2().equals(uid1)) && (match.getUid1().equals(uid2) || match.getUid2().equals(uid2))){
                return match;
            }
        }
        Match match = new Match();
        match.setUid1(uid1);
        match.setUid2(uid2);
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
