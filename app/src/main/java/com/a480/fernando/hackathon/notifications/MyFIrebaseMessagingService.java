package com.a480.fernando.hackathon.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;

import com.a480.fernando.hackathon.ICallbackActivity;
import com.a480.fernando.hackathon.NotificationsActivity;
import com.a480.fernando.hackathon.R;
import com.a480.fernando.hackathon.dao.UserDao;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;

/**
 * Created by Fernando on 04/05/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService implements ICallbackActivity {

    private UserDao userDao;
    private String body;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getNotification() != null) {
            body = remoteMessage.getNotification().getBody();
            userDao = new UserDao();
            userDao.isSnooze(this);
        }
    }

    private void sendNotification() {
        Intent notificationIntent = new Intent(this, NotificationsActivity.class);
        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        android.support.v4.app.NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.notifications_icon)
                .setContentTitle("Hackathon")
                .setContentText(body)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.notify_icon))
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                .setShowWhen(true)
                .setAutoCancel(true)
                .setVibrate(new long[] { 1000, 500, 1000})
                .setLights(Color.RED, 1000, 1000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(intent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) Calendar.getInstance().getTimeInMillis()%Integer.MAX_VALUE, notification.build());
    }

    @Override
    public void onDataLoaded() {
        if(!userDao.getSnooze()) {
            sendNotification();
        }
    }
}
