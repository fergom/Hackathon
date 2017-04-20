package com.a480.fernando.hackathon.dao;

import com.a480.fernando.hackathon.model.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fernando on 19/04/2017.
 */

public class EventsDao extends Dao {

    private final DatabaseReference myRef = database.getReference("Events");
    private ArrayList<Event> events;

    public EventsDao() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events = new ArrayList<Event>();
                HashMap<String, Object> years = (HashMap<String, Object>) dataSnapshot.getValue();
                HashMap<String, Object> months;
                HashMap<String, Object> days;
                HashMap<String, Object> daysOfWeek;
                HashMap<String, HashMap<String, String>> info;

                Event event = null;
                int year, month, day;
                String dayOfWeek;

                for(String y: years.keySet()) {
                    year = Integer.parseInt(y);
                    months = (HashMap<String, Object>) years.get(y);
                    for(String m: months.keySet()) {
                        month = Integer.parseInt(m);
                        days = (HashMap<String, Object>) months.get(m);
                        for(String d: days.keySet()) {
                            day = Integer.parseInt(d);
                            daysOfWeek = (HashMap<String, Object>) days.get(d);
                            for(String dw: daysOfWeek.keySet()) {
                                dayOfWeek = dw;
                                info = (HashMap<String, HashMap<String, String>>) daysOfWeek.get(dw);
                                for(String i: info.keySet()) {
                                    event = new Event();
                                    event.setYear(year);
                                    event.setMonth(month);
                                    event.setDay(day);
                                    event.setDayOfWeek(dayOfWeek);
                                    event.setTitle(i);
                                    event.setAddress(info.get(i).get("address"));
                                    event.setEndTime(info.get(i).get("endTime"));
                                    event.setImage(info.get(i).get("image"));
                                    event.setInfo(info.get(i).get("info"));
                                    event.setStartTime(info.get(i).get("startTime"));
                                    events.add(event);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<Event> getEventsMonth(int month) {
        ArrayList<Event> eventsMonth = new ArrayList<Event>();
        for(Event e: events) {
            if(e.getMonth() == month) {
                eventsMonth.add(e);
            }
        }
        return eventsMonth;
    }

    public Event getEventByTitle(String title) {
        for(Event e: events) {
            if(e.getTitle().equals(title)) {
                return e;
            }
        }
        return null;
    }

}
