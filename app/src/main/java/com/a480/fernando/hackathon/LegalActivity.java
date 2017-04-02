package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.webkit.WebView;

public class LegalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);
        setCloseToolBar("Legales");
        setJustifiedText();
    }

    private void setJustifiedText() {
        String htmlText = "<html><body style=\"text-align:justify;color:#888888\"> %s </body></html>";
        String description = getString(R.string.legal_info);
        description = description.replace("\n","<br>");
        WebView webView = (WebView) findViewById(R.id.legal_info);
        webView.loadData(String.format(htmlText, description), "text/html; charset=utf-8", "utf-8");
    }

}
