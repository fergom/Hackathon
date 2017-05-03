package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.webkit.WebView;

import static com.a480.fernando.hackathon.AppConstant.GREY_HEX;

public class LegalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);
        setCloseToolbar("Legales");

        WebView webView = (WebView) findViewById(R.id.legal_info);
        setJustifiedText(webView, infoDao.getLegalInfo().replace("\n","<br>"), GREY_HEX);
    }

}
