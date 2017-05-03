package com.a480.fernando.hackathon.dao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Fernando on 29/03/2017.
 */

public class Dao {

    protected final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    protected final static FirebaseAuth auth = FirebaseAuth.getInstance();
    protected static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    protected String formatDate(Calendar date) {
        StringBuilder dateString = new StringBuilder();
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH) + 1;
        int year = date.get(Calendar.YEAR);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        int second = date.get(Calendar.SECOND);
        if(day < 10) {
            dateString.append("0");
        }
        dateString.append(day + "/");
        if(month < 10) {
            dateString.append("0");
        }
        dateString.append(month + "/");
        dateString.append(year + " ");
        if(hour < 10) {
            dateString.append("0");
        }
        dateString.append(hour + ":");
        if(minute < 10) {
            dateString.append("0");
        }
        dateString.append(minute + ":");
        if(second < 10) {
            dateString.append("0");
        }
        dateString.append(second + "");
        return dateString.toString();
    }

}
