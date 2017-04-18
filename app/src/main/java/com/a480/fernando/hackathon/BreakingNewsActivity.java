package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.a480.fernando.hackathon.adapter.NewsAdapter;

public class BreakingNewsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breaking_news);

        navigation = (DrawerLayout) findViewById(R.id.activity_breaking_news);
        setToolBar("Breaking News");

        loadNews();
    }

    private void loadNews() {
        ListView newsList = (ListView) findViewById(R.id.breaking_news_list);

        NewsAdapter newsAdapter = new NewsAdapter(newsDao.getNews(), getApplicationContext());
        newsList.setAdapter(newsAdapter);
    }

}
