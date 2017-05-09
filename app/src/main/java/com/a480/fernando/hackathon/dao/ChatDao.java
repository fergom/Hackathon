package com.a480.fernando.hackathon.dao;

import com.a480.fernando.hackathon.ICallbackActivity;
import com.a480.fernando.hackathon.model.ChatMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Fernando on 08/05/2017.
 */

public class ChatDao extends Dao {

    private final DatabaseReference publicRef = database.getReference("Chats");
    private ArrayList<ChatMessage> messages;
    private ICallbackActivity callbackActivity;

    public void ChatDao() {}

    public void setCallbackActivity(ICallbackActivity callbackActivity) {
        this.callbackActivity = callbackActivity;
    }

    public void listenChat(String chatId) {

        publicRef.child(chatId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, String>> values = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                messages = new ArrayList<ChatMessage>();
                if(values != null) {
                    HashMap<String, String> data;
                    ChatMessage chatMessage;
                    Calendar time = null;
                    for(String id: values.keySet()) {
                        data = values.get(id);
                        chatMessage = new ChatMessage();
                        chatMessage.setMessage(data.get("message"));
                        chatMessage.setSenderUid(data.get("senderUid"));
                        try {
                            time = Calendar.getInstance();
                            time.setTime(sdf.parse(data.get("time").toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        chatMessage.setTime(time);
                        messages.add(chatMessage);
                    }
                }
                callbackActivity.onDataLoaded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError);
            }
        });
    }

    public void saveMessage(String chatId, ChatMessage message) {
        Calendar time = message.getTime();
        message.setTime(null);
        DatabaseReference r = publicRef.child(chatId).push();
        r.setValue(message);
        r.child("time").setValue(formatDate(time));
    }

    public ArrayList<ChatMessage> getMessages() {
        return messages;
    }

}
