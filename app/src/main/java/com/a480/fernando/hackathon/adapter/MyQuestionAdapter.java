package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a480.fernando.hackathon.AnswerActivity;
import com.a480.fernando.hackathon.AppConstant;
import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.model.Question;

import java.util.ArrayList;

import static com.a480.fernando.hackathon.AppConstant.getTime;

/**
 * Created by Fernando on 15/05/2017.
 */

public class MyQuestionAdapter extends BaseAdapter {

    private String speakerName;
    private ArrayList<Question> questions;
    private Context context;
    private AppCompatActivity activity;

    public MyQuestionAdapter(String speakerName, ArrayList<Question> questions, Context context, AppCompatActivity activity) {
        this.speakerName = speakerName;
        this.questions = questions;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.my_question_item, null);

        TextView title = (TextView) v.findViewById(R.id.question_title);
        TextView time = (TextView) v.findViewById(R.id.question_time);
        TextView answer = (TextView) v.findViewById(R.id.answered);
        TextView likes = (TextView) v.findViewById(R.id.question_likes);
        TextView comments = (TextView) v.findViewById(R.id.question_comments);

        Question question = questions.get(position);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnswerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(AppConstant.SPEAKER_NAME, speakerName);
                intent.putExtra(AppConstant.QUESTION_TITLE, question.getTitle());
                context.startActivity(intent);
                activity.finish();
            }
        });

        title.setText(question.getTitle());
        time.setText(getTime(question.getTime()));
        if(question.getAnswer().equals("No contestada.")) {
            answer.setText("NO CONTESTADA");
            answer.setTextColor(Color.RED);
        } else {
            answer.setText("CONTESTADA");
            answer.setTextColor(Color.GREEN);
        }
        likes.setText(question.getLikes().size() + "");
        comments.setText(question.getComments().size() + "");

        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public int getCount() {
        return questions.size();
    }

}
