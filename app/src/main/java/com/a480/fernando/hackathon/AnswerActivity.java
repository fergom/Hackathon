package com.a480.fernando.hackathon;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a480.fernando.hackathon.adapter.CommentAdapter;
import com.a480.fernando.hackathon.model.Comment;
import com.a480.fernando.hackathon.model.Question;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class AnswerActivity extends BaseActivity {

    private Question question;
    private String speakerName;
    private String questionTitle;
    private ArrayList<Comment> commentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        speakerName = getIntent().getExtras().getString(AppConstant.SPEAKER_NAME);
        questionTitle = getIntent().getExtras().getString(AppConstant.QUESTION_TITLE);

        question = speakersDao.getQuestion(speakerName, questionTitle);

        TextView time = (TextView) findViewById(R.id.answer_time);
        TextView likes = (TextView) findViewById(R.id.answer_likes);
        TextView comments = (TextView) findViewById(R.id.question_comments);
        WebView title = (WebView) findViewById(R.id.question_title);
        WebView answer = (WebView) findViewById(R.id.question_answer);
        ImageView questionLike = (ImageView) findViewById(R.id.like_icon);

        if(user != null) {
            if(question.getLikes().get(FirebaseAuth.getInstance().getCurrentUser().getUid()) != null) {
                questionLike.setImageResource(R.drawable.red_like);
            } else {
                questionLike.setImageResource(R.drawable.grey_like);
            }
        } else {
            questionLike.setImageResource(R.drawable.grey_like);
        }

        time.setText(getTime(question.getTime()));
        likes.setText(question.getLikes().size() + "");
        comments.setText(question.getComments().size() + "");

        setJustifiedText(title, question.getTitle(), "#000000");
        setJustifiedText(answer, question.getAnswer(), "#888888");

        questionLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null) {
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    ImageView image = (ImageView) v;
                    Drawable.ConstantState constantState;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        constantState = getResources().getDrawable(R.drawable.grey_like, getTheme()).getConstantState();
                    } else {
                        constantState = getResources().getDrawable(R.drawable.grey_like).getConstantState();
                    }
                    if(image.getDrawable().getConstantState() == constantState) {
                        speakersDao.addLike(speakerName, questionTitle, uid);
                        question.addLike(uid);
                        likes.setText(question.getLikes().size() + "");
                        image.setImageResource(R.drawable.red_like);
                    } else {
                        speakersDao.removeLike(speakerName, questionTitle, uid);
                        question.removeLike(uid);
                        likes.setText(question.getLikes().size() + "");
                        image.setImageResource(R.drawable.grey_like);
                    }
                }
            }
        });

        commentsList = question.getComments();

        Collections.sort(commentsList, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return o2.getTime().compareTo(o1.getTime());
            }
        });

        loadComments();

    }

    public void comment(View view) {
        Intent intent = new Intent(AnswerActivity.this, CreateCommentActivity.class);
        intent.putExtra(AppConstant.SPEAKER_NAME, speakerName);
        intent.putExtra(AppConstant.QUESTION_TITLE, questionTitle);
        startActivity(intent);
        finish();
    }

    public void close(View view) {
        Intent intent = new Intent(AnswerActivity.this, QuestionsActivity.class);
        intent.putExtra(AppConstant.SPEAKER_NAME, speakerName);
        startActivity(intent);
        finish();
    }

    private void setJustifiedText(WebView webView, String text, String color) {
        String htmlText = "<html><body style=\"text-align:justify;color:" + color + "\"> %s </body></html>";
        text = text.replace("\n", "<br>");
        webView.loadData(String.format(htmlText, text), "text/html; charset=utf-8", "utf-8");
    }

    private void loadComments() {
        ListView comments = (ListView) findViewById(R.id.answer_comments_list);

        CommentAdapter commentAdapter = new CommentAdapter(userDao, question.getComments(), getApplicationContext());
        comments.setAdapter(commentAdapter);
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

}
