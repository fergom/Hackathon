package com.a480.fernando.hackathon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a480.fernando.hackathon.model.Speaker;

import java.net.URL;

public class QuestionsActivity extends BaseActivity {

    private Speaker speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        speaker = speakersDao.getSpeakerByName(getIntent().getStringExtra(AppConstant.SPEAKER_NAME));

        TextView name = (TextView) findViewById(R.id.speaker_name);
        ImageView image = (ImageView) findViewById(R.id.speaker_image);

        name.setText(speaker.getName().toUpperCase());
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(speaker.getImage());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            image.setImageBitmap(bmp);
        } catch(Exception e) {
            e.printStackTrace();
        }

        loadQuestions();
    }

    private void loadQuestions() {

    }

    public void goBack(View view) {
        finish();
    }
}
