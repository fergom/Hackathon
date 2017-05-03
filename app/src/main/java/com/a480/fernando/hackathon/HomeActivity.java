package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import static com.a480.fernando.hackathon.AppConstant.GREY_HEX;

public class HomeActivity extends BaseActivity implements ICallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigation = (DrawerLayout) findViewById(R.id.activity_home);
        setToolbar("Home");
        infoDao.listenInfo(HomeActivity.this);

        Button inscription = (Button) findViewById(R.id.inscription);
        if(user != null) {
            inscription.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDataLoaded() {
        WebView webView = (WebView) findViewById(R.id.web_view_home);
        setJustifiedText(webView, infoDao.getHomeInfo().replace("\n", "<br>"), GREY_HEX);
    }

}