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

        setJustifiedText();
    }

    private void setJustifiedText() {
        String htmlText = "<html><body style=\"text-align:justify;color:#888888\"> %s </body></html>";
        String description = speaker.getDescription();
        description = description.replace("\n", "<br>");
        WebView webView = (WebView) findViewById(R.id.speaker_description);
        webView.loadData(String.format(htmlText, description), "text/html; charset=utf-8", "utf-8");
    }

    public void goToLinkedin(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(speaker.getLinkedin())));
    }

    public void goToTwitter(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(speaker.getTwitter())));
    }

    public void goToWebsite(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(speaker.getWebsite())));
    }

    public void askSpeaker(View view) {
        Intent intent = new Intent(SpeakerInfoActivity.this, QuestionsActivity.class);
        intent.putExtra(AppConstant.SPEAKER_NAME, speaker.getName());
        startActivity(intent);
    }

    public void goBack(View view) {
        finish();
    }

}
