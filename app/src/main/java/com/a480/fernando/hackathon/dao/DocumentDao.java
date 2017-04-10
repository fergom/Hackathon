package com.a480.fernando.hackathon.dao;

import android.util.Log;

import com.a480.fernando.hackathon.model.Document;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fernando on 07/04/2017.
 */

public class DocumentDao extends Dao {

    private final static DatabaseReference myRef = database.getReference("Documents");
    private static ArrayList<Document> documents;
    private static Document inscription;

    public DocumentDao() {

        documents = new ArrayList<Document>();
        inscription = new Document();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, String>> value = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                HashMap<String, String> info;
                for(String key: value.keySet()) {
                    Document doc = new Document();
                    info = value.get(key);
                    doc.setTitle(info.get("title"));
                    doc.setHref(info.get("href"));
                    if(!key.equals("inscription")) {
                        documents.add(doc);
                    } else {
                        inscription = doc;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

    public Document getInscription() {
        return inscription;
    }

    public ArrayList<Document> getDocuments() {
        return  documents;
    }

}
