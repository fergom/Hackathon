package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends BaseActivity implements ICallbackActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setToken();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userDao.setCallback(StartActivity.this);
            userDao.onAuthenticated();
        } else {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    startHome();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }

    @Override
    public void onDataLoaded() {
        startHome();
    }

    private void startHome() {
        Intent intent = new Intent(StartActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
