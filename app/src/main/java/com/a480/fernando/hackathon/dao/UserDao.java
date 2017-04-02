package com.a480.fernando.hackathon.dao;

import com.a480.fernando.hackathon.model.User;

/**
 * Created by Fernando on 29/03/2017.
 */

public class UserDao extends Dao {

    public User getUser(String nif) {
        return bbdd.getUserByNif(nif);
    }

}
