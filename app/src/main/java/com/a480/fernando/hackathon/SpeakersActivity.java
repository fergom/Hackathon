package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.a480.fernando.hackathon.adapter.HorizontalSpeakersAdapter;
import com.a480.fernando.hackathon.model.Speaker;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class SpeakersActivity extends BaseActivity {

    private MaterialSearchView searchView;
    private ArrayList<Speaker> speakers;
    private TextView noSpeakers;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakers);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        navigation = (DrawerLayout) findViewById(R.id.activity_speakers);
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
        speakers = speakersDao.getSpeakers();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

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
            recyclerView.setVisibility(View.GONE);
            noSpeakers.setVisibility(View.VISIBLE);
        } else {
            noSpeakers.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new HorizontalSpeakersAdapter(speakers, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
    }

}
