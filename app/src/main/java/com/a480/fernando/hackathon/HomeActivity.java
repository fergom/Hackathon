package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigation = (DrawerLayout) findViewById(R.id.activity_home);
        setToolBar("Home");
        setJustifiedText();

        Button inscription = (Button) findViewById(R.id.inscription);

        if(user != null) {
            inscription.setVisibility(View.GONE);
        }

    }

    private void setJustifiedText() {
        String htmlText = "<html><body style=\"text-align:justify;color:#888888\"> %s </body></html>";
        String description = infoDao.getHomeInfo();
        if(description.length() == 0) {
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            finish();
        }
        description = description.replace("\n", "<br>");
        WebView webView = (WebView) findViewById(R.id.web_view_home);
        webView.loadData(String.format(htmlText, description), "text/html; charset=utf-8", "utf-8");
    }

}