package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginCredentials(View view) {
        EditText nifEditText = (EditText) findViewById(R.id.login_nif);
        String nif = nifEditText.getText().toString();
        // Aquí se comprobará si las credenciales son correctas
        AppSharedPreferences.setUser(getApplicationContext(), nif);
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
