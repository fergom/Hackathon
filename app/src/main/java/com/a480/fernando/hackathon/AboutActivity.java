package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        navigation = (DrawerLayout) findViewById(R.id.activity_about);
        setToolbar("Acerca de");
    }

    public void legal(View view) {
        Intent intent = new Intent(AboutActivity.this, LegalActivity.class);
        startActivity(intent);
    }

    public void feedback(View view) {
        Intent intent = new Intent(AboutActivity.this, FeedBackActivity.class);
        startActivity(intent);
    }

}
