package com.a480.fernando.hackathon.dao;

import android.util.Log;

import com.a480.fernando.hackathon.model.Comment;
import com.a480.fernando.hackathon.model.Question;
import com.a480.fernando.hackathon.model.Speaker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Fernando on 10/04/2017.
 */

public class SpeakersDao extends Dao {

    private final DatabaseReference myRef = database.getReference("Speakers");
    private ArrayList<Speaker> speakers;

    public SpeakersDao() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                speakers = new ArrayList<Speaker>();
                HashMap<String, HashMap<String, Object>> value = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                HashMap<String, Object> attributes;
                ArrayList<HashMap<String, Object>> questions;
                ArrayList<HashMap<String, Object>> comments;
                HashMap<String, Integer> likes;
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
                    speaker.setImage(attributes.get("image").toString());
                    speaker.setLinkedin(attributes.get("linkedin").toString());
                    speaker.setTwitter(attributes.get("twitter").toString());
                    speaker.setWebsite(attributes.get("website").toString());
                    quest = new ArrayList<Question>();

                    questions = (ArrayList<HashMap<String, Object>>) attributes.get("questions");
                    if(questions != null) {
                        for(HashMap<String, Object> answer: questions) {
                            q = new Question();
                            q.setId((long) answer.get("id"));
                            q.setAnswer(answer.get("answer").toString());
                            q.setTitle(answer.get("title").toString());
                            likes = (HashMap<String, Integer>) answer.get("likes");
                            if(likes != null) {
                                q.setLikes(likes);
                            } else {
                                q.setLikes(new HashMap<String, Integer>());
                            }
                            try {
                                time = Calendar.getInstance();
                                time.setTime(sdf.parse(answer.get("time").toString()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            q.setTime(time);
                            com = new ArrayList<Comment>();
                            comments = (ArrayList<HashMap<String, Object>>) answer.get("comments");
                            if(comments != null) {
                                for(HashMap<String, Object> comment: comments) {
                                    c = new Comment();
                                    c.setName(comment.get("name").toString());
                                    c.setComment(comment.get("comment").toString());
                                    c.setUserImage(comment.get("userImage").toString());
                                    try {
                                        time = Calendar.getInstance();
                                        time.setTime(sdf.parse(comment.get("time").toString()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    c.setTime(time);
                                    com.add(c);
                                }
                            }
                            q.setComments(com);
                            quest.add(q);
                        }
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

    public Speaker getSpeakerByName(String name) {
        for(Speaker speaker: speakers) {
            if(speaker.getName().equals(name)) {
                return speaker;
            }
        }
        return null;
    }

    public Question getQuestion(String name, String title) {
        for(Speaker speaker: speakers) {
            if(speaker.getName().equals(name)) {
                for(Question question: speaker.getQuestions()) {
                    if(question.getTitle().equals(title)) {
                        return question;
                    }
                }
            }
        }
        return null;
    }

    public void addQuestion(String speakerName, Question question) {
        int items = 0;
        if(getSpeakerByName(speakerName).getQuestions() != null) {
            items = getSpeakerByName(speakerName).getQuestions().size();
        }
        DatabaseReference questionRef = myRef.child("/" + speakerName + "/questions/" + items);
        Calendar date = question.getTime();
        question.setTime(null);
        question.setId(items);
        questionRef.setValue(question);
        questionRef.child("time").setValue(formatDate(date));
    }

    public void addAnswer(String speakerName, long id, String answer) {
        DatabaseReference questionRef = myRef.child("/" + speakerName + "/questions/" + id);
        questionRef.child("answer").setValue(answer);
    }

    public void addComment(String speakerName, String questionTitle, Comment comment) {
        long questionId = 0;
        int commentId = 0;
        Question q = getQuestion(speakerName, questionTitle);
        questionId = q.getId();
        if(q.getComments() != null) {
            commentId = q.getComments().size();
        }
        DatabaseReference commentRef = myRef.child("/" + speakerName + "/questions/" + questionId + "/comments/" + commentId);
        Calendar date = comment.getTime();
        comment.setTime(null);
        commentRef.setValue(comment);
        commentRef.child("time").setValue(formatDate(date));
    }

    public void addLike(String speakerName, String questionTitle, String uid) {
        long questionId = getQuestion(speakerName, questionTitle).getId();
        DatabaseReference likeRef = myRef.child("/" + speakerName + "/questions/" + questionId + "/likes/" + uid);
        likeRef.setValue(1);
    }

    public void removeLike(String speakerName, String questionTitle, String uid) {
        long questionId = getQuestion(speakerName, questionTitle).getId();
        DatabaseReference likeRef = myRef.child("/" + speakerName + "/questions/" + questionId + "/likes/" + uid);
        likeRef.removeValue();
    }

}
