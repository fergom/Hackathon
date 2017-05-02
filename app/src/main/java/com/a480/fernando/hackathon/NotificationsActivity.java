package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.a480.fernando.hackathon.adapter.NotificationsAdapter;
import com.a480.fernando.hackathon.model.Notification;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class NotificationsActivity extends BaseActivity implements ICallbackActivity {

    private ListView notificationsListView;
    private NotificationsAdapter notificationsAdapter;
    private ArrayList<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        navigation = (DrawerLayout) findViewById(R.id.activity_notifications);
        setToolBar("Notificaciones");

        loadNotifications();
    }

    private void loadNotifications() {
        notificationsDao.setCallbackActivity(NotificationsActivity.this);
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            notificationsDao.listenUserNotifications(user.getUid());
        }
        notificationsDao.listenPublicNotifications();
        notificationsListView = (ListView) findViewById(R.id.notifications_list);
    }

    @Override
    public void onDataLoaded() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            notifications = notificationsDao.getUserNotifications();
        } else {
            notifications = notificationsDao.getPublicNotifications();
        }
        notificationsAdapter = new NotificationsAdapter(notifications, getApplicationContext());
        notificationsListView.setAdapter(notificationsAdapter);
    }
}