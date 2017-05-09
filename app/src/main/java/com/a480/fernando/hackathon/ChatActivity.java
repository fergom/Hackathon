package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.a480.fernando.hackathon.adapter.ChatAdapter;
import com.a480.fernando.hackathon.model.ChatMessage;
import com.a480.fernando.hackathon.model.User;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends BaseActivity implements ICallbackActivity {

    private User reveiverUser;
    private String chatId;
    private ArrayList<ChatMessage> messages;
    private EditText sendMessage;
    private ListView chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        reveiverUser = userDao.getUserUid(getIntent().getStringExtra(AppConstant.UID));
        chatId = getIntent().getStringExtra(AppConstant.CHAT_ID);

        chatDao.setCallbackActivity(this);
        chatDao.listenChat(chatId);

        chatList = (ListView) findViewById(R.id.chat_list);
        sendMessage = (EditText) findViewById(R.id.chat_message);
        TextView name = (TextView) findViewById(R.id.chat_name);
        CircleImageView profile = (CircleImageView) findViewById(R.id.chat_profile_image);

        name.setText(reveiverUser.getName() + " " + reveiverUser.getSurname());
        Glide.with(getApplicationContext()).load(reveiverUser.getImage()).into(profile);
    }

    public void sendMessage(View view) {
        if(sendMessage.getText().length() > 0) {
            ChatMessage message = new ChatMessage();
            message.setTime(Calendar.getInstance());
            message.setMessage(sendMessage.getText().toString());
            message.setSenderUid(user.getUid());
            chatDao.saveMessage(chatId, message);
            sendMessage.setText("");
        }
    }

    @Override
    public void onDataLoaded() {
        messages = chatDao.getMessages();

        Collections.sort(messages, new Comparator<ChatMessage>() {
            @Override
            public int compare(ChatMessage o1, ChatMessage o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        Calendar day = Calendar.getInstance();
        day.set(Calendar.YEAR, 2000);
        ArrayList<ChatMessage> filteredMessages = new ArrayList<ChatMessage>();

        for(ChatMessage c: messages) {
            if(checkDay(day, c.getTime())) {
                ChatMessage separator = new ChatMessage();
                separator.setTime(c.getTime());
                separator.setFirstOfDay(true);
                filteredMessages.add(separator);
                day = c.getTime();
            }
            filteredMessages.add(c);
        }

        ChatAdapter chatAdapter = new ChatAdapter(filteredMessages, getApplicationContext(), user.getUid());
        chatList.setAdapter(chatAdapter);
        chatList.setSelection(chatAdapter.getCount()-1);
    }

    private boolean checkDay(Calendar day, Calendar time) {
        if(day.get(Calendar.YEAR) < time.get(Calendar.YEAR)) {
            return true;
        } else if(day.get(Calendar.YEAR) == time.get(Calendar.YEAR)) {
            if(day.get(Calendar.DAY_OF_YEAR) < time.get(Calendar.DAY_OF_YEAR)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
