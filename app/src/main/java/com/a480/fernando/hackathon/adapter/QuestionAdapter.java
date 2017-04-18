package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a480.fernando.hackathon.AnswerActivity;
import com.a480.fernando.hackathon.AppConstant;
import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.model.Question;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Fernando on 11/04/2017.
 */

public class QuestionAdapter extends BaseAdapter {

    private String speakerName;
    private ArrayList<Question> questions;
    private Context context;
    private AppCompatActivity activity;

    public QuestionAdapter(String speakerName, ArrayList<Question> questions, Context context, AppCompatActivity activity) {
        this.speakerName = speakerName;
        this.questions = questions;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.question_item, null);

        TextView title = (TextView) v.findViewById(R.id.question_title);
        TextView time = (TextView) v.findViewById(R.id.question_time);
        TextView answer = (TextView) v.findViewById(R.id.question_answer);
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
        answer.setText(question.getAnswer());
        likes.setText(question.getLikes().size() + "");
        comments.setText(question.getComments().size() + "");

        try {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if(question.getLikes().get(uid) != null) {
                ImageView like = (ImageView) v.findViewById(R.id.like_icon_question_item);
                like.setImageResource(R.drawable.red_like);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    private String getTime(Calendar time) {
        String difference;
        long diff = Calendar.getInstance().getTime().getTime() - time.getTime().getTime();
        double minutes = diff / (1000 * 60);
        if (minutes < 60) {
            difference = minutes + " minutos";
        } else {
            long horas = (long) (minutes / 60);
            if(horas < 24) {
                difference = horas + " horas";
            } else {
                long dias = horas/24;
                difference = dias + " días.";
            }
        }
        return difference;
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
