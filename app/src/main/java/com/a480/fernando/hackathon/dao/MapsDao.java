package com.a480.fernando.hackathon.dao;

import android.util.Log;

import com.a480.fernando.hackathon.AppConstant;
import com.a480.fernando.hackathon.model.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fernando on 29/03/2017.
 */

public class MapsDao extends Dao {

    private final DatabaseReference myRef = database.getReference("Maps");
    private ArrayList<Service> services;
    private ArrayList<Service> facilities;

    public MapsDao() {

        services = new ArrayList<Service>();
        facilities = new ArrayList<Service>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, Object>> value = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                HashMap<String, Object> attributes;
                String type;
                Service service;
                for(String name: value.keySet()) {
                    service = new Service();
                    attributes = value.get(name);
                    service.setName(name);
                    type = attributes.get("type").toString();
                    if(!type.contains("image")) {
                        service.setAddress(attributes.get("address").toString());
                        service.setLatitude((double) attributes.get("latitude"));
                        service.setLongitude((double) attributes.get("longitude"));
                        service.setDescription(attributes.get("description").toString());
                    }
                    if(type.contains("facility")) {
                        if(type.contains("image")) {
                            service.setType(AppConstant.IMAGE);
                        } else {
                            service.setType(AppConstant.LOCATION);
                        }
                        facilities.add(service);
                    } else {
                        service.setPhone(attributes.get("phone").toString());
                        if(type.contains("hotel")) {
                            service.setType(AppConstant.HOTELS);
                        } else {
                            service.setType(AppConstant.OTHERS);
                        }
                        services.add(service);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });

    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public Service getServiceByName(String name) {
        for(Service service: services) {
            if(service.getName().equals(name)) {
                return service;
            }
        }
        return null;
    }

    public ArrayList<Service> getFacilities() {
        return facilities;
    }

    public Service getFacilityByName(String name) {
        for(Service facility: facilities) {
            if(facility.getName().equals(name)) {
                return facility;
            }
        }
        return null;
    }

}
