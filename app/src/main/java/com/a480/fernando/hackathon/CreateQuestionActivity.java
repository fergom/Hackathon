package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.a480.fernando.hackathon.model.Comment;
import com.a480.fernando.hackathon.model.Question;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateQuestionActivity extends BaseActivity {

    private String speakerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        speakerName = getIntent().getExtras().getString(AppConstant.SPEAKER_NAME);

        setGoBackToolBar("Añadir pregunta");
    }

    public void ask(View view) {
        Question question = new Question();

        EditText questionText = (EditText) findViewById(R.id.question_text);
        question.setTitle(questionText.getText().toString());
        question.setTime(Calendar.getInstance());
        question.setAnswer("No contestada.");
        question.setComments(new ArrayList<Comment>());

        speakersDao.addQuestion(speakerName, question);

        Intent intent = new Intent(CreateQuestionActivity.this, QuestionsActivity.class);
        intent.putExtra(AppConstant.SPEAKER_NAME, speakerName);
        startActivity(intent);
        finish();
    }

}
