package com.a480.fernando.hackathon;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class AccreditationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accreditation);

        navigation = (DrawerLayout) findViewById(R.id.activity_accreditation);
        setToolbar("Acreditación");

        if(user != null) {
            TextView name = (TextView) findViewById(R.id.accreditation_name);
            TextView company = (TextView) findViewById(R.id.accreditation_company);
            TextView id = (TextView) findViewById(R.id.accreditation_id);
            ImageView image = (ImageView) findViewById(R.id.accreditation_image);
            ImageView qr = (ImageView) findViewById(R.id.accreditation_qr);
            name.setText(user.getName() + " " + user.getSurname());
            id.setText("ID: " + user.getUid().toUpperCase());
            company.setText(user.getCompanyName());
            Glide.with(getApplicationContext()).load(user.getImage()).into(image);

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(user.getUid(), BarcodeFormat.QR_CODE,120,120);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                qr.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}
