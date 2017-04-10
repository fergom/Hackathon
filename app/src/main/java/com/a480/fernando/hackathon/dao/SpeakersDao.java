package com.a480.fernando.hackathon.dao;

import android.util.Log;

import com.a480.fernando.hackathon.model.Comment;
import com.a480.fernando.hackathon.model.Question;
import com.a480.fernando.hackathon.model.Speaker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Fernando on 10/04/2017.
 */

public class SpeakersDao extends Dao {

    private final DatabaseReference myRef = database.getReference("Speakers");
    private ArrayList<Speaker> speakers;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public SpeakersDao() {

        speakers = new ArrayList<Speaker>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, Object>> value = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                HashMap<String, Object> attributes;
                ArrayList<HashMap<String, Object>> questions;
                ArrayList<HashMap<String, Object>> comments;
                Speaker speaker;
                ArrayList<Question> quest;
                Question q;
                Calendar time = null;
                ArrayList<Comment> com;
                Comment c;
                for(String key: value.keySet()) {
                    speaker = new Speaker();
                    attributes = value.get(key);
                    speaker.setName(key);
                    speaker.setDescription(attributes.get("description").toString());
                    speaker.setJob(attributes.get("job").toString());
                    speaker.setLinkedin(attributes.get("linkedin").toString());
                    speaker.setTwitter(attributes.get("twitter").toString());
                    speaker.setWebsite(attributes.get("website").toString());
                    quest = new ArrayList<Question>();

                    questions = (ArrayList<HashMap<String, Object>>) attributes.get("questions");
                    for(HashMap<String, Object> answer: questions) {
                        q = new Question();
                        q.setAnswer(answer.get("answer").toString());
                        q.setTitle(answer.get("title").toString());
                        q.setLikes((long) answer.get("likes"));
                        try {
                            time = Calendar.getInstance();
                            time.setTime(sdf.parse(answer.get("time").toString()));
                        } catch (ParseException pe) {
                            pe.printStackTrace();
                        }
                        q.setTime(time);
                        com = new ArrayList<Comment>();
                        comments = (ArrayList<HashMap<String, Object>>) answer.get("comments");
                        for(HashMap<String, Object> comment: comments) {
                            c = new Comment();
                            c.setName(comment.get("name").toString());
                            c.setComment(comment.get("comment").toString());
                            try {
                                time = Calendar.getInstance();
                                time.setTime(sdf.parse(comment.get("time").toString()));
                            } catch (ParseException pe) {
                                pe.printStackTrace();
                            }
                            c.setTime(time);
                            com.add(c);
                        }
                        q.setComments(com);
                        quest.add(q);
                    }
                    speaker.setQuestions(quest);
                    speakers.add(speaker);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });

    }

    public ArrayList<Speaker> getSpeakers() {
        return speakers;
    }

}
