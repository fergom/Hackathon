package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.webkit.WebView;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigation = (DrawerLayout) findViewById(R.id.activity_home);
        setToolBar("Home");
        setJustifiedText();
        if(user != null) {
            findViewById(R.id.login).setVisibility(View.GONE);
            findViewById(R.id.inscription).setVisibility(View.GONE);
        } else {
            findViewById(R.id.login).setVisibility(View.VISIBLE);
            findViewById(R.id.inscription).setVisibility(View.VISIBLE);
        }
    }

    private void setJustifiedText() {
        String htmlText = "<html><body style=\"text-align:justify;color:#888888\"> %s </body></html>";
        String description = getString(R.string.home_description);
        description = description.replace("\n","<br>");
        WebView webView = (WebView) findViewById(R.id.web_view_home);
        webView.loadData(String.format(htmlText, description), "text/html; charset=utf-8", "utf-8");
    }

    public void login(View view) {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
