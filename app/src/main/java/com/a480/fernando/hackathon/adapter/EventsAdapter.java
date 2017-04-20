package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a480.fernando.hackathon.AppConstant;
import com.a480.fernando.hackathon.EventInfoActivity;
import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.model.Event;

import java.util.ArrayList;

/**
 * Created by Fernando on 19/04/2017.
 */

public class EventsAdapter extends BaseAdapter {

    private ArrayList<Event> events;
    private Context context;

    public EventsAdapter(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;
        Event event = events.get(position);

        if(!event.getSeparator()) {
            v = View.inflate(context, R.layout.events_list_item, null);

            TextView hour = (TextView) v.findViewById(R.id.event_item_hour);
            TextView title = (TextView) v.findViewById(R.id.event_item_title);

            hour.setText(event.getStartTime());
            title.setText(event.getTitle());

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(AppConstant.EVENT_TITLE, event.getTitle());
                    context.startActivity(intent);
                }
            });

        } else {
            v = View.inflate(context, R.layout.events_list_separator_item, null);

            TextView day = (TextView) v.findViewById(R.id.event_list_separator_day);
            TextView dayOfWeek = (TextView) v.findViewById(R.id.event_list_separator_day_of_week);

            day.setText(event.getDay() + "");
            dayOfWeek.setText(event.getDayOfWeek());

            v.setClickable(false);
        }

        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public int getCount() {
        return events.size();
    }

}