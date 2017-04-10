package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SuccessfulRegistrationActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_registration);
        TextView registrationMessage = (TextView) findViewById(R.id.registration_message);

        registrationMessage.setText("¡Enhorabuena " + this.user.getName() + "  te has registrado correctamente en el formulario!");

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SuccessfulRegistrationActivity.this,ProfileActivity.class);
                SuccessfulRegistrationActivity.this.startActivity(mainIntent);
                SuccessfulRegistrationActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
