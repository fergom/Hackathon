package com.a480.fernando.hackathon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class NoConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
    }

    public void checkConnection(View view) {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMgr.getActiveNetworkInfo() != null) {
            Intent intent = new Intent(NoConnectionActivity.this, StartActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Sigue sin haber conexión.", Toast.LENGTH_LONG).show();
        }
    }

}
