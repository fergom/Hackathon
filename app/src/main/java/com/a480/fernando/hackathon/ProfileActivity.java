package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

        if(user != null) {
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(user.getName() + " " + user.getSurname());
        }
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
