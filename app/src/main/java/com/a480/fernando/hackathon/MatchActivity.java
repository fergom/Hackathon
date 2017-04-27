package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        CircleImageView image1 = (CircleImageView) findViewById(R.id.image1);
        CircleImageView image2 = (CircleImageView) findViewById(R.id.image2);
        TextView name1 = (TextView) findViewById(R.id.name1);
        TextView name2 = (TextView) findViewById(R.id.name2);
        TextView surname1 = (TextView) findViewById(R.id.surname1);
        TextView surname2 = (TextView) findViewById(R.id.surname2);

        Glide.with(getApplicationContext()).load(getIntent().getStringExtra(AppConstant.IMAGE_1)).into(image1);
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra(AppConstant.IMAGE_2)).into(image2);
        name1.setText(getIntent().getStringExtra(AppConstant.NAME_1));
        name2.setText(getIntent().getStringExtra(AppConstant.NAME_2));
        surname1.setText(getIntent().getStringExtra(AppConstant.SURNAME_1));
        surname2.setText(getIntent().getStringExtra(AppConstant.SURNAME_2));
    }

    public void close(View view) {
        startActivity(new Intent(MatchActivity.this, NetworkingActivity.class));
        finish();
    }

    public void call(View view) {
        Toast.makeText(getApplicationContext(), "LLAMAR", Toast.LENGTH_LONG).show();
    }

    public void sendEmail(View view) {
        Toast.makeText(getApplicationContext(), "EMAIL", Toast.LENGTH_LONG).show();
    }

}
