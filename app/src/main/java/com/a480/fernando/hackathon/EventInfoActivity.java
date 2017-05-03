package com.a480.fernando.hackathon;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a480.fernando.hackathon.model.Event;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.TimeZone;

public class EventInfoActivity extends BaseActivity {

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        event = eventsDao.getEventByTitle(getIntent().getStringExtra(AppConstant.EVENT_TITLE));

        ImageView image = (ImageView) findViewById(R.id.event_image);
        TextView title = (TextView) findViewById(R.id.event_title_detail);
        TextView hour = (TextView) findViewById(R.id.event_hour_detail);
        TextView address = (TextView) findViewById(R.id.event_location_detail);
        TextView info = (TextView) findViewById(R.id.event_info_detail);

        if(event.getImage() != null) {
            Glide.with(getApplicationContext()).load(event.getImage()).into(image);
        }

        title.setText(event.getTitle());
        hour.setText(event.getStartTime() + " - " + event.getEndTime());
        address.setText(event.getAddress());
        info.setText(event.getInfo());

    }

    public void addCalendar(View view) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_CALENDAR}, 1);
        } else {
            final ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.CALENDAR_ID, 1);

            values.put(CalendarContract.Events.TITLE, event.getTitle());
            values.put(CalendarContract.Events.DESCRIPTION, event.getInfo());
            values.put(CalendarContract.Events.EVENT_LOCATION, event.getAddress());

            Calendar start = Calendar.getInstance();
            start.set(event.getYear(), event.getMonth(), event.getDay(),Integer.parseInt(event.getStartTime().split(":")[0]),Integer.parseInt(event.getStartTime().split(":")[1].substring(0,2)),0);
            Calendar end = Calendar.getInstance();
            end.set(event.getYear(), event.getMonth(), event.getDay(),Integer.parseInt(event.getEndTime().split(":")[0]),Integer.parseInt(event.getEndTime().split(":")[1].substring(0,2)),0);
            values.put(CalendarContract.Events.DTSTART, start.getTimeInMillis());
            values.put(CalendarContract.Events.DTEND, end.getTimeInMillis());
            values.put(CalendarContract.Events.ALL_DAY, 0);   // 0 for false, 1 for true
            values.put(CalendarContract.Events.HAS_ALARM, 1); // 0 for false, 1 for true

            String timeZone = TimeZone.getDefault().getID();
            values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);

            Uri baseUri;
            if (Build.VERSION.SDK_INT >= 8) {
                baseUri = Uri.parse("content://com.android.calendar/events");
            } else {
                baseUri = Uri.parse("content://calendar/events");
            }

            getApplicationContext().getContentResolver().insert(baseUri, values);
            Toast.makeText(getApplicationContext(), "El evento se ha añadido correctamente a tu calendario con una alarma de aviso.", Toast.LENGTH_LONG).show();
        }
    }

}
