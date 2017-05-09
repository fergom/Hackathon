package com.a480.fernando.hackathon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.model.ChatMessage;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Fernando on 08/05/2017.
 */

public class ChatAdapter extends BaseAdapter {

    private ArrayList<ChatMessage> messages;
    private Context context;
    private String userUid;

    public ChatAdapter(ArrayList<ChatMessage> messages, Context context, String userUid) {
        this.messages = messages;
        this.context = context;
        this.userUid = userUid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage = messages.get(position);
        View v;

        if(chatMessage.isFirstOfDay()) {
            v = View.inflate(context, R.layout.chat_separator_item, null);
            TextView day = (TextView) v.findViewById(R.id.day_separator);
            day.setText(formatDay(chatMessage.getTime()));
        } else {
            if(chatMessage.getSenderUid().equals(userUid)) {
                v = View.inflate(context, R.layout.sender_message_chat_item, null);
            } else {
                v = View.inflate(context, R.layout.receiver_message_chat_item, null);
            }

            TextView message = (TextView) v.findViewById(R.id.message);
            TextView time = (TextView) v.findViewById(R.id.time);

            message.setText(chatMessage.getMessage());

            Calendar hour = chatMessage.getTime();
            String h = hour.get(Calendar.HOUR_OF_DAY) + ":";
            if(hour.get(Calendar.MINUTE) < 10) {
                h += "0";
            }
            h += hour.get(Calendar.MINUTE) + "h";
            time.setText(h);
        }

        return v;
    }

    private String formatDay(Calendar time) {
        int m = time.get(Calendar.MONTH) + 1;
        return time.get(Calendar.DAY_OF_MONTH) + "/" + m + "/" + time.get(Calendar.YEAR);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

}
