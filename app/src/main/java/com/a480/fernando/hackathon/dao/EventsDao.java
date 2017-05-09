package com.a480.fernando.hackathon.dao;

import com.a480.fernando.hackathon.model.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Fernando on 19/04/2017.
 */

public class EventsDao extends Dao {

    private final DatabaseReference myRef = database.getReference("Events");
    private ArrayList<Event> events;
    protected static final SimpleDateFormat sdfEvent = new SimpleDateFormat("dd/MM/yyyy");

    public EventsDao() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events = new ArrayList<Event>();
                HashMap<String, Object> eventNames = (HashMap<String, Object>) dataSnapshot.getValue();
                HashMap<String, String> data;
                Event event;
                Calendar time = null;

                for(String name: eventNames.keySet()) {
                    event = new Event();
                    event.setTitle(name);
                    data = (HashMap<String, String>) eventNames.get(name);
                    event.setAddress(data.get("address"));
                    event.setDayOfWeek(data.get("dayOfWeek"));
                    event.setEndTime(data.get("endTime"));
                    event.setStartTime(data.get("startTime"));
                    event.setInfo(data.get("info"));
                    event.setImage(data.get("image"));
                    try {
                        time = Calendar.getInstance();
                        time.setTime(sdfEvent.parse(data.get("time")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    event.setYear(time.get(Calendar.YEAR));
                    event.setMonth(time.get(Calendar.MONTH));
                    event.setDay(time.get(Calendar.DAY_OF_MONTH));
                    events.add(event);
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
