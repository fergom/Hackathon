package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

public class SponsorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
        navigation = (DrawerLayout) findViewById(R.id.activity_sponsor);
        setToolBar("Patrocinadores");
    }
}
