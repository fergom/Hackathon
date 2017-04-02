package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SentSuggestionActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_suggestion);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SentSuggestionActivity.this,AboutActivity.class);
                SentSuggestionActivity.this.startActivity(mainIntent);
                SentSuggestionActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
