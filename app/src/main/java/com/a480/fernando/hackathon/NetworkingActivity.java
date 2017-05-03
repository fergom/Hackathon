package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a480.fernando.hackathon.model.Match;
import com.a480.fernando.hackathon.model.Notification;
import com.a480.fernando.hackathon.model.User;
import com.bumptech.glide.Glide;

import java.util.Calendar;

public class NetworkingActivity extends BaseActivity {

    private User randomUser;
    private Match match;

    private LinearLayout activateLayout;
    private LinearLayout networkingLayout;

    private ImageView profile;
    private TextView name;
    private TextView job;
    private TextView interest;

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking);

        counter = 0;

        navigation = (DrawerLayout) findViewById(R.id.activity_networking);
        setToolbar("Networking");

        activateLayout = (LinearLayout) findViewById(R.id.activate_layout);
        networkingLayout = (LinearLayout) findViewById(R.id.networking_layout);

        if(user.getNetworking()) {
            onCreateNetworking();
        } else {
            onCreateNotNetworking();
        }
    }

    private void onCreateNotNetworking() {
        activateLayout.setVisibility(View.VISIBLE);
        networkingLayout.setVisibility(View.GONE);
    }

    private void onCreateNetworking() {
        activateLayout.setVisibility(View.GONE);
        networkingLayout.setVisibility(View.VISIBLE);

        ImageView toolbarRightImageView = (ImageView) findViewById(R.id.toolbar_right_icon);
        toolbarRightImageView.setImageResource(R.drawable.networking_new_requests_icon);
        toolbarRightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetworkingActivity.this, ContactsActivity.class));
            }
        });

        profile = (ImageView) findViewById(R.id.networking_match_image);
        name = (TextView) findViewById(R.id.networking_match_name);
        job = (TextView) findViewById(R.id.networking_match_job);
        interest = (TextView) findViewById(R.id.networking_match_interest);
        loadRandomUser();
    }

    private void loadRandomUser() {
        randomUser = userDao.getRandomUser(user.getEmail());

        if(randomUser == null) {
            Toast.makeText(getApplicationContext(), "No hay más usuarios con los que hacer match.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(NetworkingActivity.this, ProfileActivity.class));
        }

        match = matchDao.getMatch(user.getEmail(), randomUser.getEmail());

        if((match.getAnswer1() == true && match.getMatch1() == false) || (match.getAnswer2() == true && match.getMatch2() == false)) {
            checkCounter();
        } else {
            if(match.getEmail1().equals(user.getEmail())) {
                if(match.getAnswer1()) {
                    checkCounter();
                }
            } else {
                if(match.getAnswer2()) {
                    checkCounter();
                }
            }
        }

        Glide.with(getApplicationContext()).load(randomUser.getImage()).into(profile);
        name.setText(randomUser.getName() + " " + randomUser.getSurname());
        job.setText(randomUser.getCity());
        interest.setText(randomUser.getState());
    }

    public void activate(View view) {
        user.setNetworking(true);
        userDao.saveUser(user);
        onCreateNetworking();
    }

    public void accept(View view) {
       if(match.getEmail1().equals(user.getEmail())) {
           match.setMatch1(true);
           match.setAnswer1(true);
       } else {
           match.setMatch2(true);
           match.setAnswer2(true);
       }
        matchDao.saveMatch(match);
        if(match.getMatch1() && match.getMatch2()) {
            Notification n1 = new Notification();
            Notification n2 = new Notification();
            n1.setMessage("Tienes un match con " + randomUser.getName() + " " + randomUser.getSurname());
            n1.setTime(Calendar.getInstance());
            n2.setMessage("Tienes un match con " + user.getName() + " " + user.getSurname());
            n2.setTime(Calendar.getInstance());
            notificationsDao.addNotification(randomUser.getUid(), n2);
            notificationsDao.addNotification(user.getUid(), n1);
            Intent intent = new Intent(NetworkingActivity.this, MatchActivity.class);
            intent.putExtra(AppConstant.IMAGE_1, user.getImage());
            intent.putExtra(AppConstant.IMAGE_2, randomUser.getImage());
            intent.putExtra(AppConstant.NAME_1, user.getName());
            intent.putExtra(AppConstant.NAME_2, randomUser.getName());
            intent.putExtra(AppConstant.SURNAME_1, user.getSurname());
            intent.putExtra(AppConstant.SURNAME_2, randomUser.getSurname());
            intent.putExtra(AppConstant.EMAIL, randomUser.getEmail());
            intent.putExtra(AppConstant.PHONE_NUMBER, randomUser.getPhoneNumber());
            startActivity(intent);
        } else {
            Intent intent = new Intent(NetworkingActivity.this, WaitMatchActivity.class);
            intent.putExtra(AppConstant.NAME_1, randomUser.getName());
            intent.putExtra(AppConstant.SURNAME_1, randomUser.getSurname());
            startActivity(intent);
        }
    }

    public void cancel(View view) {
        if(match.getEmail1().equals(user.getEmail())) {
            match.setMatch1(false);
            match.setAnswer1(true);
        } else {
            match.setMatch2(false);
            match.setAnswer2(true);
        }
        matchDao.saveMatch(match);
        Intent intent = new Intent(NetworkingActivity.this, CancelMatchActivity.class);
        intent.putExtra(AppConstant.NAME_1, randomUser.getName());
        intent.putExtra(AppConstant.SURNAME_1, randomUser.getSurname());
        startActivity(intent);
    }

    private void checkCounter() {
        if(counter == 5) {
            counter = 0;
            Toast.makeText(getApplicationContext(), "No hay más usuarios con los que hacer match.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(NetworkingActivity.this, ProfileActivity.class));
        } else {
            counter++;
            loadRandomUser();
        }
    }

}
