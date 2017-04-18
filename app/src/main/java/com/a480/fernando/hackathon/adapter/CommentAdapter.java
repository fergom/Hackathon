package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.dao.UserDao;
import com.a480.fernando.hackathon.model.Comment;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Fernando on 12/04/2017.
 */

public class CommentAdapter extends BaseAdapter {

    private UserDao userDao;
    private ArrayList<Comment> comments;
    private Context context;

    public CommentAdapter(UserDao userDao, ArrayList<Comment> comments, Context context) {
        this.userDao = userDao;
        this.comments = comments;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.comment_item, null);

        TextView name = (TextView) v.findViewById(R.id.name_comment);
        TextView time = (TextView) v.findViewById(R.id.time_comment);
        TextView userComment = (TextView) v.findViewById(R.id.comment_comment);
        CircleImageView profile = (CircleImageView) v.findViewById(R.id.profile_image_comment);

        Comment comment = this.comments.get(position);

        name.setText(comment.getName());
        time.setText(getTime(comment.getTime()));
        userComment.setText(comment.getComment());

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(comment.getUserImage());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            profile.setImageBitmap(bmp);
        } catch(Exception e) {
            e.printStackTrace();
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
        return comments.get(position);
    }

    @Override
    public int getCount() {
        return comments.size();
    }

}
