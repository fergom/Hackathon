package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.a480.fernando.hackathon.adapter.AskSpeakerAdapter;
import com.a480.fernando.hackathon.model.Speaker;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class AskSpeakerListActivity extends BaseActivity {

    private ListView speakersListView;
    private AskSpeakerAdapter askSpeakerAdapter;
    private MaterialSearchView searchView;
    private ArrayList<Speaker> speakers;
    private TextView noSpeakers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_speaker_list);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        navigation = (DrawerLayout) findViewById(R.id.activity_ask_speaker_list);
        setToolbar("Ponentes");

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() { }

            @Override
            public void onSearchViewClosed() {
                speakers = speakersDao.getSpeakers();
                loadSpeakers();
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return onQueryTextChange(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    newText = newText.toLowerCase();
                    ArrayList<Speaker> querySpeakers = new ArrayList<Speaker>();
                    for(Speaker s: speakersDao.getSpeakers()) {
                        if(s.getName().toLowerCase().contains(newText)) {
                            querySpeakers.add(s);
                        }
                    }
                    speakers = querySpeakers;
                    loadSpeakers();
                } else {
                    speakers = speakersDao.getSpeakers();
                    loadSpeakers();
                }
                return true;
            }
        });

        noSpeakers = (TextView) findViewById(R.id.no_speakers);
        speakersListView = (ListView) findViewById(R.id.speakers_list);
        speakers = speakersDao.getSpeakers();
        loadSpeakers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    private void loadSpeakers() {
        if(speakers.size() == 0) {
            noSpeakers.setVisibility(View.VISIBLE);
            speakersListView.setVisibility(View.GONE);
        } else {
            noSpeakers.setVisibility(View.GONE);
            speakersListView.setVisibility(View.VISIBLE);
            askSpeakerAdapter = new AskSpeakerAdapter(speakers, getApplicationContext());
            speakersListView.setAdapter(askSpeakerAdapter);
        }
    }

}
