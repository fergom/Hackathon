package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a480.fernando.hackathon.adapter.QuestionAdapter;
import com.a480.fernando.hackathon.model.Question;
import com.a480.fernando.hackathon.model.Speaker;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QuestionsActivity extends BaseActivity {

    private Speaker speaker;
    private ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        speaker = speakersDao.getSpeakerByName(getIntent().getStringExtra(AppConstant.SPEAKER_NAME));

        TextView name = (TextView) findViewById(R.id.speaker_name);
        ImageView image = (ImageView) findViewById(R.id.speaker_image);

        name.setText(speaker.getName().toUpperCase());
        Glide.with(getApplicationContext()).load(speaker.getImage()).into(image);

        questions = speaker.getQuestions();

        Collections.sort(questions, new Comparator<Question>() {
            @Override
            public int compare(Question o1, Question o2) {
                return o2.getTime().compareTo(o1.getTime());
            }
        });

        loadQuestions();
    }

    private void loadQuestions() {
        ListView questionsListView = (ListView) findViewById(R.id.questions_list);

        QuestionAdapter questionAdapter = new QuestionAdapter(speaker.getName(), questions, getApplicationContext(), this);
        questionsListView.setAdapter(questionAdapter);
    }

    public void ask(View view) {
        Intent intent = new Intent(QuestionsActivity.this, CreateQuestionActivity.class);
        intent.putExtra(AppConstant.SPEAKER_NAME, speaker.getName());
        startActivity(intent);
        finish();
    }
}
