package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.a480.fernando.hackathon.adapter.MyQuestionAdapter;
import com.a480.fernando.hackathon.model.Question;
import com.a480.fernando.hackathon.model.Speaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyQuestionsActivity extends BaseActivity {

    private ArrayList<Question> questions;
    private Speaker speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_questions);

        navigation = (DrawerLayout) findViewById(R.id.activity_my_questions);
        setToolbar("Mis preguntas");

        speaker = speakersDao.getSpeakerByName(user.getName() + " " + user.getSurname());
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
        ListView questionsListView = (ListView) findViewById(R.id.my_questions_list);

        MyQuestionAdapter myQuestionAdapter = new MyQuestionAdapter(user.getName() + " " + user.getSurname(), questions, getApplicationContext(), this);
        questionsListView.setAdapter(myQuestionAdapter);
    }

}
