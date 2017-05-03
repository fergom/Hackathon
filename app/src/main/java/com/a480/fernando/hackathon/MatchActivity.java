package com.a480.fernando.hackathon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchActivity extends BaseActivity {

    private String email;
    private String phoneNumber;

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

        email = getIntent().getStringExtra(AppConstant.EMAIL);
        phoneNumber =  getIntent().getStringExtra(AppConstant.PHONE_NUMBER);
    }

    public void close(View view) {
        Intent intent = new Intent(MatchActivity.this, NetworkingActivity.class);
        startActivity(intent);
        finish();
    }

    public void call(View view) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CALL_PHONE}, 1);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Para poder llamar debes dar permisos a la aplicación.", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }
    }

    public void sendEmail(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hackathon");
            intent = Intent.createChooser(intent, "Enviar email...");
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "No se ha podido enviar el email.", Toast.LENGTH_SHORT).show();
        }
    }

}
