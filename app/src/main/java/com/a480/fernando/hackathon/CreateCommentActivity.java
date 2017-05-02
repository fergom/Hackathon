package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.a480.fernando.hackathon.model.Comment;

import java.util.Calendar;

public class CreateCommentActivity extends BaseActivity {

    private String speakerName;
    private String questionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        questionTitle = getIntent().getExtras().getString(AppConstant.QUESTION_TITLE);
        speakerName = getIntent().getExtras().getString(AppConstant.SPEAKER_NAME);

        setGoBackToolBar("Añadir comentario");
    }

    public void commentAnswer(View view) {
        Comment comment = new Comment();

        EditText commentText = (EditText) findViewById(R.id.comment_text);
        comment.setComment(commentText.getText().toString());
        comment.setTime(Calendar.getInstance());
        if(user != null) {
            comment.setName(user.getName() + " " + user.getSurname());
            comment.setUserImage(user.getImage());
        } else {
            comment.setName("Anónimo");
            comment.setUserImage("https://firebasestorage.googleapis.com/v0/b/hackathon-4d513.appspot.com/o/profile.png?alt=media&token=3e4335fc-5095-402a-b751-06fd85108805");
        }

        speakersDao.addComment(speakerName, questionTitle, comment);

        Intent intent = new Intent(CreateCommentActivity.this, AnswerActivity.class);
        intent.putExtra(AppConstant.SPEAKER_NAME, speakerName);
        intent.putExtra(AppConstant.QUESTION_TITLE, questionTitle);
        startActivity(intent);
        finish();
    }

}
