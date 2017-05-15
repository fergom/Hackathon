package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AnswerQuestionActivity extends BaseActivity {

    private String speakerName;
    private String questionTitle;
    private long questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);

        questionId = getIntent().getExtras().getLong(AppConstant.QUESTION_ID);
        speakerName = getIntent().getExtras().getString(AppConstant.SPEAKER_NAME);
        questionTitle = getIntent().getExtras().getString(AppConstant.QUESTION_TITLE);

        setGoBackToolbar("Contestar pregunta");
    }

    public void answerQuestion(View view) {
        EditText answerText = (EditText) findViewById(R.id.answer_question_text);
        if(answerText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "La contestación no puede estar vacía.", Toast.LENGTH_LONG).show();
        } else {
            speakersDao.addAnswer(speakerName, questionId, answerText.getText().toString());
        }

        Intent intent = new Intent(AnswerQuestionActivity.this, AnswerActivity.class);
        intent.putExtra(AppConstant.SPEAKER_NAME, speakerName);
        intent.putExtra(AppConstant.QUESTION_TITLE, questionTitle);
        startActivity(intent);
        finish();
    }

}
