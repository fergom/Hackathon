package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a480.fernando.hackathon.AppConstant;
import com.a480.fernando.hackathon.QuestionsActivity;
import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.SpeakerInfoActivity;
import com.a480.fernando.hackathon.model.Speaker;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Fernando on 10/04/2017.
 */

public class SpeakerAdapter extends BaseAdapter {

    private ArrayList<Speaker> speakers;
    private Context context;

    public SpeakerAdapter(ArrayList<Speaker> speakers, Context context) {
        this.speakers = speakers;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.speaker_item, null);

        ImageView speakerImage = (ImageView) v.findViewById(R.id.speaker_image);
        TextView name = (TextView) v.findViewById(R.id.speaker_name);
        TextView job = (TextView) v.findViewById(R.id.speaker_job);
        WebView description = (WebView) v.findViewById(R.id.speaker_description);
        ImageView linkedin = (ImageView) v.findViewById(R.id.linkedin);
        ImageView twitter = (ImageView) v.findViewById(R.id.twitter);
        ImageView website = (ImageView) v.findViewById(R.id.website);
        Button askSpeaker = (Button) v.findViewById(R.id.ask_speaker);

        Speaker speaker = speakers.get(position);

        askSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askSpeaker(speaker);
            }
        });

        speakerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpeakerInfo(speaker);
            }
        });

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLink(speaker.getLinkedin());
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLink(speaker.getTwitter());
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLink(speaker.getWebsite());
            }
        });

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(speaker.getImage());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            speakerImage.setImageBitmap(bmp);
        } catch(Exception e) {
            e.printStackTrace();
        }

        name.setText(speaker.getName().toUpperCase());
        job.setText(speaker.getJob());

        String htmlText = "<html><body style=\"text-align:justify;color:#888888\"> %s </body></html>";
        String desc = speaker.getDescription();
        desc = desc.replace("\n", "<br>");
        description.loadData(String.format(htmlText, desc), "text/html; charset=utf-8", "utf-8");

        return v;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return speakers.get(position);
    }

    @Override
    public int getCount() {
        return speakers.size();
    }

    private void showSpeakerInfo(Speaker speaker) {
        Intent intent = new Intent(context, SpeakerInfoActivity.class);
        intent.putExtra(AppConstant.SPEAKER_NAME, speaker.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(intent);
    }

    private void goToLink(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(browserIntent);

    }

    private void askSpeaker(Speaker speaker) {
        Intent intent = new Intent(this.context, QuestionsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AppConstant.SPEAKER_NAME, speaker.getName());
        this.context.startActivity(intent);
    }

}
