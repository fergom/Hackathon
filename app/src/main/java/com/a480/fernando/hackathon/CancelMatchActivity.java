package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CancelMatchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_match);

        TextView message = (TextView) findViewById(R.id.message);
        message.setText("Se ha registrado correctamente que no quieres ser amigo de " +
                getIntent().getStringExtra(AppConstant.NAME_1) + " "
                + getIntent().getStringExtra(AppConstant.SURNAME_1)
                + ". Tu perfil no saldrá en su lista de sugerencias.");
    }

    public void close(View view) {
        startActivity(new Intent(CancelMatchActivity.this, NetworkingActivity.class));
        finish();
    }

}
