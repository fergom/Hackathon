package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a480.fernando.hackathon.AppConstant;
import com.a480.fernando.hackathon.QuestionsActivity;
import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.SpeakerInfoActivity;
import com.a480.fernando.hackathon.model.Speaker;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Fernando on 03/05/2017.
 */

public class HorizontalSpeakersAdapter extends RecyclerView.Adapter<HorizontalSpeakersAdapter.ViewHolder> {

    private ArrayList<Speaker> speakers;
    private Context context;

    public HorizontalSpeakersAdapter(ArrayList<Speaker> speakers, Context context) {
        this.speakers = speakers;
        this.context = context;
    }

    @Override
    public HorizontalSpeakersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.speaker_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(HorizontalSpeakersAdapter.ViewHolder holder, int position) {
        Speaker speaker = speakers.get(position);
        holder.askSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askSpeaker(speaker);
            }
        });

        holder.speakerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpeakerInfo(speaker);
            }
        });

        holder.linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLink(speaker.getLinkedin());
            }
        });

        holder.twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLink(speaker.getTwitter());
            }
        });

        holder.website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLink(speaker.getWebsite());
            }
        });

        Glide.with(context).load(speaker.getImage()).into(holder.speakerImage);
        holder.name.setText(speaker.getName().toUpperCase());
        holder.job.setText(speaker.getJob());
        holder.description.setText(speaker.getDescription());
    }

    @Override
    public int getItemCount() {
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView speakerImage;
        public TextView name;
        public TextView job;
        public TextView description;
        public ImageView linkedin;
        public ImageView twitter;
        public ImageView website;
        public Button askSpeaker;

        public ViewHolder(View itemView) {
            super(itemView);
            speakerImage = (ImageView) itemView.findViewById(R.id.speaker_image);
            name = (TextView) itemView.findViewById(R.id.speaker_name);
            job = (TextView) itemView.findViewById(R.id.speaker_job);
            description = (TextView) itemView.findViewById(R.id.speaker_description);
            linkedin = (ImageView) itemView.findViewById(R.id.linkedin);
            twitter = (ImageView) itemView.findViewById(R.id.twitter);
            website = (ImageView) itemView.findViewById(R.id.website);
            askSpeaker = (Button) itemView.findViewById(R.id.ask_speaker);
        }
    }
}
