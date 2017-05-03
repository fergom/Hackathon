package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.dao.UserDao;
import com.a480.fernando.hackathon.model.Comment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.a480.fernando.hackathon.AppConstant.getTime;

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
        Glide.with(context).load(comment.getUserImage()).into(profile);

        return v;
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
