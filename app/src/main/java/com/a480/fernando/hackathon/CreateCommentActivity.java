package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.a480.fernando.hackathon.model.Comment;

import java.util.Calendar;

import static com.a480.fernando.hackathon.AppConstant.DEFAULT_PROFILE_IMAGE_URL;

public class CreateCommentActivity extends BaseActivity {

    private String speakerName;
    private String questionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        questionTitle = getIntent().getExtras().getString(AppConstant.QUESTION_TITLE);
        speakerName = getIntent().getExtras().getString(AppConstant.SPEAKER_NAME);

        setGoBackToolbar("Añadir comentario");
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
            comment.setUserImage(DEFAULT_PROFILE_IMAGE_URL);
        }

        speakersDao.addComment(speakerName, questionTitle, comment);

        Intent intent = new Intent(CreateCommentActivity.this, AnswerActivity.class);
        intent.putExtra(AppConstant.SPEAKER_NAME, speakerName);
        intent.putExtra(AppConstant.QUESTION_TITLE, questionTitle);
        startActivity(intent);
        finish();
    }

}
