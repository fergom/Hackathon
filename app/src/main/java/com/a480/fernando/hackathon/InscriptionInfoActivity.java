package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.webkit.WebView;

public class InscriptionInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_info);
        navigation = (DrawerLayout) findViewById(R.id.activity_inscription_info);
        setToolBar("Inscripción");
        setJustifiedText();
    }

    private void setJustifiedText() {
        String htmlText = "<html><body style=\"text-align:justify;color:#888888\"> %s </body></html>";
        String description = infoDao.getInscriptionInfo();
        description = description.replace("\n","<br>");
        WebView webView = (WebView) findViewById(R.id.web_view_inscription_info);
        webView.loadData(String.format(htmlText, description), "text/html; charset=utf-8", "utf-8");
    }

    public void goToInscription(View view) {
        Intent intent = new Intent(InscriptionInfoActivity.this, InscriptionFormActivity.class);
        startActivity(intent);
    }

}
