package com.a480.fernando.hackathon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class NetworkingActivity extends BaseActivity {

    private LinearLayout activateLayout;
    private LinearLayout networkingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking);

        navigation = (DrawerLayout) findViewById(R.id.activity_networking);
        setToolBar("Networking");

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
                Toast.makeText(getApplicationContext(), "FRIENDS", Toast.LENGTH_LONG).show();
            }
        });

        ImageView profile = (ImageView) findViewById(R.id.networking_match_image);
        TextView name = (TextView) findViewById(R.id.networking_match_name);
        TextView job = (TextView) findViewById(R.id.networking_match_job);
        TextView interest = (TextView) findViewById(R.id.networking_match_interest);

        Glide.with(getApplicationContext()).load(user.getImage()).into(profile);
        name.setText(user.getName() + " " + user.getSurname());
        job.setText(user.getCity());
        interest.setText(user.getCompanyName());
    }

    public void activate(View view) {
        user.setNetworking(true);
        userDao.saveUser(user);
        onCreateNetworking();
    }

    public void accept(View view) {
        Toast.makeText(getApplicationContext(), "ACCEPT", Toast.LENGTH_LONG).show();
    }

    public void cancel(View view) {
        Toast.makeText(getApplicationContext(), "CANCEL", Toast.LENGTH_LONG).show();
    }

}
