package com.a480.fernando.hackathon.dao;

import com.a480.fernando.hackathon.model.Service;

import java.util.ArrayList;

/**
 * Created by Fernando on 29/03/2017.
 */

public class ServiceDao extends Dao {

    public ArrayList<Service> getServices() {
        return bbdd.getServices();
    }

    public Service getService(int position) {
        return bbdd.getService(position);
    }

    public Service getServiceByName(String name) {
        return bbdd.getServiceByName(name);
    }

}
