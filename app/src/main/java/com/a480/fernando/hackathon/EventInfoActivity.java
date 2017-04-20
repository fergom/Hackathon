package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a480.fernando.hackathon.model.Event;
import com.bumptech.glide.Glide;

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
        Toast.makeText(getApplicationContext(), "Add to calendar", Toast.LENGTH_LONG).show();
    }

    public void goBack(View view) {
        finish();
    }

}
