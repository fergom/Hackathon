package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class FeedBackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        setCloseToolBar("Feedback");
    }

    public void sendFeedback(View view) {
        EditText suggestionEditText = (EditText) findViewById(R.id.suggestion_text);
        String suggestion = suggestionEditText.getText().toString();
        Log.e("TAG", suggestion);
        Intent intent = new Intent(FeedBackActivity.this, SentSuggestionActivity.class);
        startActivity(intent);
    }

}
