package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.webkit.WebView;

import static com.a480.fernando.hackathon.AppConstant.GREY_HEX;

public class InscriptionInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_info);
        navigation = (DrawerLayout) findViewById(R.id.activity_inscription_info);
        setToolbar("Inscripción");

        WebView webView = (WebView) findViewById(R.id.web_view_inscription_info);
        setJustifiedText(webView, infoDao.getInscriptionInfo().replace("\n","<br>"), GREY_HEX);
    }

    public void goToInscription(View view) {
        Intent intent = new Intent(InscriptionInfoActivity.this, InscriptionFormActivity.class);
        startActivity(intent);
    }

}
