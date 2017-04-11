package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a480.fernando.hackathon.AppConstant;
import com.a480.fernando.hackathon.QuestionsActivity;
import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.model.Speaker;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Fernando on 11/04/2017.
 */

public class AskSpeakerAdapter extends BaseAdapter {

    private ArrayList<Speaker> speakers;
    private Context context;

    public AskSpeakerAdapter(ArrayList<Speaker> speakers, Context context) {
        this.speakers = speakers;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.ask_speaker_item, null);

        ImageView speakerImage = (ImageView) v.findViewById(R.id.ask_speaker_image);
        TextView name = (TextView) v.findViewById(R.id.ask_speaker_name);

        Speaker speaker = speakers.get(position);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(AppConstant.SPEAKER_NAME, speaker.getName());
                context.startActivity(intent);
            }
        });

        name.setText(speaker.getName().toUpperCase());

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(speaker.getImage());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            speakerImage.setImageBitmap(bmp);
        } catch(Exception e) {
            e.printStackTrace();
        }

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

}
