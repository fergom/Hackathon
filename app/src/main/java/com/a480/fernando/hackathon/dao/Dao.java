package com.a480.fernando.hackathon.dao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Fernando on 29/03/2017.
 */

public class Dao {

    protected final static BBDD bbdd = new BBDD();
    protected final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    protected final static FirebaseAuth auth = FirebaseAuth.getInstance();

}
