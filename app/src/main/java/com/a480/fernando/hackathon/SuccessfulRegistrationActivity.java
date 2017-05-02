package com.a480.fernando.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class SuccessfulRegistrationActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_registration);
        TextView registrationMessage = (TextView) findViewById(R.id.registration_message);

        createPdf();
        registrationMessage.setText("¡Enhorabuena " + this.user.getName() + " te has registrado correctamente en el formulario!");

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SuccessfulRegistrationActivity.this,ProfileActivity.class);
                SuccessfulRegistrationActivity.this.startActivity(mainIntent);
                SuccessfulRegistrationActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public void createPdf() {
        try {
            Document doc = new Document();
            String outhPath = Environment.getExternalStorageDirectory() + "/inscription.pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(outhPath));
            doc.open();
            doc.add(new Paragraph("Inscripción de " + user.getName() + " " + user.getSurname() + " para el Hackathon 2016."));
            doc.close();

            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://hackathon-4d513.appspot.com/");
            StorageReference documentRef = storageRef.child(user.getUid() + ".pdf");
            Uri file = Uri.fromFile(new File(outhPath));
            UploadTask uploadTask = documentRef.putFile(file);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), "MAL", Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "BIEN", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
