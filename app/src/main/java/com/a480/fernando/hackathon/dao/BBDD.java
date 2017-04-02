package com.a480.fernando.hackathon.dao;

import com.a480.fernando.hackathon.AppConstant;
import com.a480.fernando.hackathon.model.Service;
import com.a480.fernando.hackathon.model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Fernando on 29/03/2017.
 */

public class BBDD {

    private static List<User> users;
    private static ArrayList<Service> services;

    public BBDD() {
        users = new LinkedList<User>();
        services = new ArrayList<Service>();
        chargeUsers();
        chargeServices();
    }

    public User getUserByNif(String nif) {
        for(User user: users) {
            if(user.getNif().equals(nif)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public Service getService(int position) {
        return services.get(position);
    }

    public Service getServiceByName(String name) {
        for(Service service: services) {
            if(service.getName().equals(name)) {
                return service;
            }
        }
        return null;
    }

    private void chargeUsers() {
        User user = new User();
        user.setFact(true);
        user.setDepartment("Software");
        user.setPosition("Jefe");
        user.setSector("Android");
        user.setCity("Castellón de la Plana");
        user.setCompanyName("Cuatroochenta");
        user.setCountry("España");
        user.setName("Fernando");
        user.setNif("20487835X");
        user.setPhoneNumber("655283422");
        user.setPostalCode("12004");
        user.setState("Comunidad Valenciana");
        user.setSurname("Gómez");
        user.setWebsite("http://cuatroochenta.com");
        users.add(user);
    }

    private void chargeServices() {
        services.add(new Service("Hotel H2 Castellón", "Carrer de Carcaixent, 3, 12005 Castellón", "Telf: 964 72 38 25", AppConstant.HOTELS, 39.981672, -0.034762));
        services.add(new Service("Hotel Bag", "Av. Hermanos Bou, 154, 12100 El Grao de Castellón", "Telf: 964 22 89 10", AppConstant.HOTELS, 39.975381, -0.001645));
        services.add(new Service("McDonald's", "Avenida del Mar, s/n, 12003 Castellón", "Telf: 964 27 00 15", AppConstant.OTHERS, 39.983033, -0.022885));
        services.add(new Service("Sopa de Lletres", "Plaza Bucarest, 3, 12003 Castellón", "Telf: 964 06 50 05", AppConstant.OTHERS, 39.981232, -0.026576));
        services.add(new Service("Hotel Doña Lola", "Calle de Lucena, 3, 12006 Castellón", "Telf: 964 21 40 11", AppConstant.HOTELS, 39.986837, -0.047425));
        services.add(new Service("Mesón Navarro", "Carrer d'Amadeu I, 6-8, 12001 Castellón", "Telf: 964 25 09 66", AppConstant.OTHERS, 39.987819, -0.040607));
        services.add(new Service("Centro Comercial Salera", "N-340a, Km 64,5, 12006 Castellón", "Telf: 964 20 28 38", AppConstant.OTHERS, 39.979385, -0.063308));
        services.add(new Service("El Corte Inglés", "Paseo de Morella, 1, 12006 Castellón", "Telf: 964 34 41 50", AppConstant.OTHERS, 39.987747, -0.047861));
    }

}
