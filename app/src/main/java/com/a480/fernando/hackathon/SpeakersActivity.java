package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a480.fernando.hackathon.adapter.SpeakerAdapter;

public class SpeakersActivity extends BaseActivity {

    private ListView speakersListView;
    private static SpeakerAdapter speakerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakers);

        navigation = (DrawerLayout) findViewById(R.id.activity_speakers);
        setToolBar("");
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("Ponentes");
        ImageView toolbarRightImageView = (ImageView) findViewById(R.id.toolbar_right_icon);
        toolbarRightImageView.setImageResource(R.drawable.search);
        toolbarRightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "BUSCAR", Toast.LENGTH_LONG).show();
            }
        });

        loadSpeakers();
    }

    private void loadSpeakers() {
        speakersListView = (ListView) findViewById(R.id.speakers_list);

        speakerAdapter = new SpeakerAdapter(speakersDao.getSpeakers(), getApplicationContext());
        speakersListView.setAdapter(speakerAdapter);
    }

}
