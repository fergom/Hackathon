package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.a480.fernando.hackathon.adapter.NewsAdapter;
import com.a480.fernando.hackathon.model.BreakingNew;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BreakingNewsActivity extends BaseActivity {

    private ArrayList<BreakingNew> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breaking_news);

        navigation = (DrawerLayout) findViewById(R.id.activity_breaking_news);
        setToolBar("Breaking News");

        news = newsDao.getNews();

        Collections.sort(news, new Comparator<BreakingNew>() {
            @Override
            public int compare(BreakingNew o1, BreakingNew o2) {
                return o2.getTime().compareTo(o1.getTime());
            }
        });

        loadNews();
    }

    private void loadNews() {
        ListView newsList = (ListView) findViewById(R.id.breaking_news_list);

        NewsAdapter newsAdapter = new NewsAdapter(newsDao.getNews(), getApplicationContext());
        newsList.setAdapter(newsAdapter);
    }

}
