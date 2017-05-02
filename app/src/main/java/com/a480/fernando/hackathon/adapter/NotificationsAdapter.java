package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.model.Notification;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Fernando on 20/04/2017.
 */

public class NotificationsAdapter extends BaseAdapter {

    private ArrayList<Notification> notifications;
    private Context context;

    public NotificationsAdapter(ArrayList<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.notification_item, null);

        TextView time = (TextView) v.findViewById(R.id.notification_item_time);
        TextView message = (TextView) v.findViewById(R.id.notification_item_message);

        Notification notification = notifications.get(position);

        time.setText(getTime(notification.getTime()));
        message.setText(notification.getMessage());

        return v;
    }

    private String getTime(Calendar time) {
        String difference;
        long diff = Calendar.getInstance().getTime().getTime() - time.getTime().getTime();
        double minutes = diff / (1000 * 60);
        if (minutes < 60) {
            difference = minutes + " minutos";
        } else {
            long horas = (long) (minutes / 60);
            if(horas < 24) {
                difference = horas + " horas";
            } else {
                long dias = horas/24;
                difference = dias + " días.";
            }
        }
        return difference;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return notifications.get(position);
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

}