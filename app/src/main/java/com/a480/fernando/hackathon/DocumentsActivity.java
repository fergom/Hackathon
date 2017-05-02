package com.a480.fernando.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a480.fernando.hackathon.adapter.DocumentAdapter;
import com.a480.fernando.hackathon.model.Document;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class DocumentsActivity extends BaseActivity {

    private Document inscription;
    private ArrayList<Document> documents;
    private ListView documentsListView;

    private LinearLayout inscriptionLayout;
    private TextView inscriptionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        inscription = documentDao.getInscription();
        documents = documentDao.getDocuments();

        inscriptionLayout = (LinearLayout) findViewById(R.id.inscription_layout);
        inscriptionTitle = (TextView) findViewById(R.id.inscription_title);

        if(user != null) {
            inscriptionLayout.setVisibility(View.VISIBLE);
            inscriptionTitle.setText(inscription.getTitle());
        }

        documentsListView = (ListView) findViewById(R.id.documents_list);

        navigation = (DrawerLayout) findViewById(R.id.activity_documents);
        setToolBar("Documentos");

        loadDocuments();
    }

    private void loadDocuments() {
        documentsListView = (ListView) findViewById(R.id.documents_list);

        DocumentAdapter documentAdapter = new DocumentAdapter(documents, getApplicationContext());
        documentsListView.setAdapter(documentAdapter);
        documentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(Document document: documents) {
                    if(document.getTitle().equals(view.getTag().toString())) {
                        openDocument(document.getHref());
                    }
                }
            }
        });
    }

    public void openInscription(View view) {
        openDocument(user.getUid() + ".pdf");
    }

    public void openDocument(String href) {
        try {
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://hackathon-4d513.appspot.com/");
            File localFile = new File(getApplicationContext().getExternalFilesDir(null), href);
            StorageReference inscription =  storageRef.child(href);
            System.out.println(Uri.fromFile(localFile));

            inscription.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.setDataAndType(Uri.fromFile(localFile),"application/pdf");
                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    Intent intent = Intent.createChooser(target, "Abrir PDF");
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), "No se ha podido abrir el PDF.", Toast.LENGTH_LONG);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
