package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        navigation = (DrawerLayout) findViewById(R.id.activity_profile);
        setToolBar("");
        ImageView toolbarRightImageView = (ImageView) findViewById(R.id.toolbar_right_icon);
        toolbarRightImageView.setImageResource(R.drawable.log_out);
        toolbarRightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });

        TextView name = (TextView) findViewById(R.id.name);
        CircleImageView imageMenu = (CircleImageView) findViewById(R.id.profile_user_image);
        Switch snooze = (Switch) findViewById(R.id.snooze_profile);
        Switch networking = (Switch) findViewById(R.id.networking_profile);

        name.setText(user.getName() + " " + user.getSurname());
        Glide.with(getApplicationContext()).load(user.getImage()).into(imageMenu);
        snooze.setChecked(user.getSnooze());
        networking.setChecked(user.getNetworking());

        snooze.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.setSnooze(isChecked);
                userDao.saveUser(user);
            }
        });

        networking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.setNetworking(isChecked);
                userDao.saveUser(user);
            }
        });
    }

    public void logout(View view) {
        userDao.logout();
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void editProfile(View view) {
        Intent intent = new Intent(ProfileActivity.this, EditProfileFormActivity.class);
        startActivity(intent);
    }

}
