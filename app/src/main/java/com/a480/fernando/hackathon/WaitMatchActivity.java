package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WaitMatchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_match);

        TextView message = (TextView) findViewById(R.id.message);
        message.setText("La petición de amistad se ha registrado correctamente. Cuando " +
                getIntent().getStringExtra(AppConstant.NAME_1) + " "
                + getIntent().getStringExtra(AppConstant.SURNAME_1)
                + " acepte se te enviará una notificación.");
    }

    public void close(View view) {
        Intent intent = new Intent(WaitMatchActivity.this, NetworkingActivity.class);
        startActivity(intent);
        finish();
    }

}
