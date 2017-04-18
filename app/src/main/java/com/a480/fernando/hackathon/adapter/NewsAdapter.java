package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.model.BreakingNew;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Fernando on 18/04/2017.
 */

public class NewsAdapter extends BaseAdapter {

    private ArrayList<BreakingNew> news;
    private Context context;

    public NewsAdapter(ArrayList<BreakingNew> news, Context context) {
        this.news = news;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.news_item, null);

        TextView time = (TextView) v.findViewById(R.id.news_time);
        TextView title = (TextView) v.findViewById(R.id.news_title);
        TextView info = (TextView) v.findViewById(R.id.news_info);
        LinearLayout image = (LinearLayout) v.findViewById(R.id.news_image);

        BreakingNew breakingNew = this.news.get(position);

        title.setText(breakingNew.getTitle().toString());
        info.setText(breakingNew.getInfo().toString());
        time.setText(getTime(breakingNew.getTime()));

        if(breakingNew.getImage() != null) {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(breakingNew.getImage());
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                BitmapDrawable background = new BitmapDrawable(bmp);
                image.setBackground(background);
                image.setVisibility(View.VISIBLE);
            } catch(Exception e) {
                e.printStackTrace();
            }
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
        return news.get(position);
    }

    @Override
    public int getCount() {
        return news.size();
    }

}
