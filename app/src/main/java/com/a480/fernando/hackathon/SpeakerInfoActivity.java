package com.a480.fernando.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.a480.fernando.hackathon.model.Speaker;
import com.bumptech.glide.Glide;

import static com.a480.fernando.hackathon.AppConstant.GREY_HEX;

public class SpeakerInfoActivity extends BaseActivity {

    private Speaker speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_info);

        speaker = speakersDao.getSpeakerByName(getIntent().getStringExtra(AppConstant.SPEAKER_NAME));

        ImageView image = (ImageView) findViewById(R.id.speaker_image);
        TextView name = (TextView) findViewById(R.id.speaker_name);
        TextView job = (TextView) findViewById(R.id.speaker_job);

        Glide.with(getApplicationContext()).load(speaker.getImage()).into(image);
        name.setText(speaker.getName().toUpperCase());
        job.setText(speaker.getJob());

        WebView webView = (WebView) findViewById(R.id.speaker_description);
        setJustifiedText(webView, speaker.getDescription().replace("\n", "<br>"), GREY_HEX);
    }

    public void goToLinkedin(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(speaker.getLinkedin()));
        startActivity(intent);
    }

    public void goToTwitter(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(speaker.getTwitter()));
        startActivity(intent);
    }

    public void goToWebsite(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(speaker.getWebsite()));
        startActivity(intent);
    }

    public void askSpeaker(View view) {
        Intent intent = new Intent(SpeakerInfoActivity.this, QuestionsActivity.class);
        intent.putExtra(AppConstant.SPEAKER_NAME, speaker.getName());
        startActivity(intent);
    }

}
