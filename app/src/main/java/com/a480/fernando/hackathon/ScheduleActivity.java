package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.a480.fernando.hackathon.adapter.EventsAdapter;
import com.a480.fernando.hackathon.model.Event;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class ScheduleActivity extends BaseActivity {

    private AppBarLayout appBarLayout;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private CompactCalendarView compactCalendarView;
    private Toolbar toolbar;
    private TextView noEvents;
    private ListView eventsListView;
    private boolean isExpanded = false;
    private int m = 0;
    private int year = 2017;
    private static final String[] months = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    private static final String[] days = {"L","M","M","J","V","S","D"};
    private ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        setScheduleToolbar();
        setNavigation();

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setDayColumnNames(days);
        compactCalendarView.setShouldDrawDaysHeader(true);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setTitle(dateFormat.format(dateClicked));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setTitle(dateFormat.format(firstDayOfNewMonth));
            }
        });

        noEvents = (TextView) findViewById(R.id.no_events);
        eventsListView = (ListView) findViewById(R.id.events_list);
        setCurrentDate(new Date());

        final ImageView arrow = (ImageView) findViewById(R.id.date_picker_arrow);

        LinearLayout datePickerButton = (LinearLayout) findViewById(R.id.date_picker_button);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    ViewCompat.animate(arrow).rotation(0).start();
                    appBarLayout.setExpanded(false, true);
                    isExpanded = false;
                } else {
                    ViewCompat.animate(arrow).rotation(180).start();
                    appBarLayout.setExpanded(true, true);
                    isExpanded = true;
                }
            }
        });
    }

    private void setScheduleToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setNavigation() {
        navigation = (DrawerLayout) findViewById(R.id.activity_schedule);
        toggle = new ActionBarDrawerToggle(this, navigation, toolbar, R.string.open, R.string.close);
        navigation.addDrawerListener(toggle);
        toggle.syncState();
        updateNavigation();
    }

    private void setCurrentDate(Date date) {
        setTitle(dateFormat.format(date));
        if (compactCalendarView != null) {
            compactCalendarView.setCurrentDate(date);
        }

    }

    private void setTitle(String subtitle) {
        TextView datePickerTextView = (TextView) findViewById(R.id.date_picker_text_view);
        m = parseInt(subtitle.split("/")[1]) - 1;
        year = parseInt(subtitle.split("/")[2]);

        if (datePickerTextView != null) {
            datePickerTextView.setText(months[m] + " " + year);
        }

        loadEvents();
    }

    private void loadEvents() {
        events = eventsDao.getEventsMonth(m);

        if(events.size() == 0) {
            noEvents.setVisibility(View.VISIBLE);
            eventsListView.setVisibility(View.GONE);
            noEvents.setText("No hay eventos en " + months[m] + " del " + year + ".");
        } else {
            Collections.sort(events, new Comparator<Event>() {
                @Override
                public int compare(Event e1, Event e2) {
                    if(e1.getYear() == e2.getYear()) {
                        if(e1.getMonth() == e2.getMonth()) {
                            if(e1.getDay() == e2.getDay()) {
                                int hour1 = parseInt(e1.getHour().split(":")[0]);
                                int hour2 = parseInt(e2.getHour().split(":")[0]);
                                if(hour1 == hour2) {
                                    int min1 = Integer.parseInt(e1.getHour().split(":")[1].substring(0,2));
                                    int min2 = Integer.parseInt(e2.getHour().split(":")[1].substring(0,2));
                                }
                                return hour1 - hour2;
                            }
                            return e1.getDay() - e2.getDay();
                        }
                        return e1.getMonth() - e2.getMonth();
                    }
                    return e1.getYear() - e2.getYear();
                }
            });

            ArrayList<Event> eventsSeparator = new ArrayList<Event>();
            Event separator = new Event();
            separator.setDay(events.get(0).getDay());
            separator.setDayOfWeek(events.get(0).getDayOfWeek());
            separator.setSeparator(true);
            eventsSeparator.add(separator);
            int lastDay = separator.getDay();

            for(Event e: events) {
                e.setSeparator(false);
                if(e.getDay() > lastDay) {
                    separator = new Event();
                    separator.setDay(e.getDay());
                    separator.setDayOfWeek(e.getDayOfWeek());
                    separator.setSeparator(true);
                    eventsSeparator.add(separator);
                    lastDay = e.getDay();
                }
                eventsSeparator.add(e);
            }

            EventsAdapter eventsAdapter = new EventsAdapter(eventsSeparator, getApplicationContext());
            eventsListView.setAdapter(eventsAdapter);

            noEvents.setVisibility(View.GONE);
            eventsListView.setVisibility(View.VISIBLE);
        }
    }

}
