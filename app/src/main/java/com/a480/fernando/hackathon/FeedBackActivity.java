package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.a480.fernando.hackathon.model.Feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedBackActivity extends BaseActivity {

    private Spinner feedbackTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        setCloseToolBar("Feedback");

        feedbackTypeSpinner = (Spinner) findViewById(R.id.feedback_type);

        List<String> types = new ArrayList<String>();
        types.add("Sugerencia");
        types.add("Queja");

        ArrayAdapter<String> yourAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, types);
        feedbackTypeSpinner.setAdapter(yourAdapter);
        feedbackTypeSpinner.setDropDownVerticalOffset(140);

    }

    public void sendFeedback(View view) {
        EditText suggestionEditText = (EditText) findViewById(R.id.suggestion_text);
        Feedback feedback = new Feedback();
        feedback.setFeedback(suggestionEditText.getText().toString());
        feedback.setType(feedbackTypeSpinner.getSelectedItem().toString());
        feedbackDao.saveFeedback(feedback);
        Intent intent = new Intent(FeedBackActivity.this, SentSuggestionActivity.class);
        startActivity(intent);
    }

}
