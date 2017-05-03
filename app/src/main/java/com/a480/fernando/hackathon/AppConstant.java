package com.a480.fernando.hackathon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Fernando on 15/03/2017.
 */

public class AppConstant {

    public static final String DEFAULT_PROFILE_IMAGE_URL = "https://firebasestorage.googleapis.com/v0/b/hackathon-4d513.appspot.com/o/profile.png?alt=media&token=3e4335fc-5095-402a-b751-06fd85108805";

    public static final String SERVICE_NAME = "service_name";
    public static final String FACILITY_NAME = "facility_name";
    public static final String SPEAKER_NAME = "speaker_name";
    public static final String QUESTION_TITLE = "question_title";
    public static final String BREAKING_NEW_TITLE = "breaking_new_title";
    public static final String EVENT_TITLE = "event_title";

    public static final double DEFAULT_LATITUDE = 39.9863563;
    public static final double DEFAULT_LONGITUDE = -0.0513246;
    public static final float DEFAULT_ZOOM = 14.0f;
    public static final float NEAR_ZOOM = 18.0f;

    public static final String OTHERS = "Otros";
    public static final String HOTELS = "Alojamiento";
    public static final String LOCATION = "Ubicacion";
    public static final String IMAGE = "Imagen";

    public static final String IMAGE_1 = "image_1";
    public static final String IMAGE_2 = "image_2";
    public static final String NAME_1 = "name_1";
    public static final String NAME_2 = "name_2";
    public static final String SURNAME_1 = "surname_1";
    public static final String SURNAME_2 = "surname_2";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";

    public static final String BLACK_HEX = "#000000";
    public static final String GREY_HEX = "#888888";

    public static final String[] MONTHS = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    public static final String[] DAYS = {"L","M","M","J","V","S","D"};

    public static final ArrayList<String> COUNTRIES = new ArrayList<>(Arrays.asList("País *", "España"));
    public static final ArrayList<String> STATES = new ArrayList<>(Arrays.asList("Comunidad *", "Andalucía", "Aragón", "Asturias", "Canarias", "Cantabria", "Castilla y León", "Castilla la Mancha", "Cataluña", "Ceuta", "Comunidad Valenciana", "Comunidad de Madrid", "Extremadura", "Galicia", "Islas Baleares", "La Rioja", "Melilla", "Murcia", "Navarra", "País Vasco"));
    public static final ArrayList<String> CITIES = new ArrayList<>(Arrays.asList("Ciudad *"));

    public static final ArrayList<String> ANDALUCIA = new ArrayList<>(Arrays.asList("Almería", "Cádiz", "Córdoba", "Granada", "Huelva", "Jaen", "Málaga", "Sevilla"));
    public static final ArrayList<String> ARAGON = new ArrayList<>(Arrays.asList("Huesca", "Teruel", "Zaragoza"));
    public static final ArrayList<String> ASTURIAS = new ArrayList<>(Arrays.asList("Asturias"));
    public static final ArrayList<String> CANARIAS = new ArrayList<>(Arrays.asList("Tenerife", "Gran Canaria"));
    public static final ArrayList<String> CANTABRIA = new ArrayList<>(Arrays.asList("Cantabria"));
    public static final ArrayList<String> CASTILLA_LEON = new ArrayList<>(Arrays.asList("Ávila", "Burgos", "León", "Palencia", "Salamanca", "Segovia", "Soria", "Valladolid", "Zamora"));
    public static final ArrayList<String> CASTILLA_MANCHA = new ArrayList<>(Arrays.asList("Albacete", "Ciudad Real", "Cuenca", "Guadalajara", "Toledo"));
    public static final ArrayList<String> CATALUNYA = new ArrayList<>(Arrays.asList("Barcelona", "Lérida", "Gerona", "Tarragona"));
    public static final ArrayList<String> CEUTA = new ArrayList<>(Arrays.asList("Ceuta"));
    public static final ArrayList<String> COM_VAL = new ArrayList<>(Arrays.asList("Alicante", "Castellón", "Valencia"));
    public static final ArrayList<String> MADRID = new ArrayList<>(Arrays.asList("Madrid"));
    public static final ArrayList<String> EXTREMADURA = new ArrayList<>(Arrays.asList("Badajoz", "Cáceres"));
    public static final ArrayList<String> GALICIA = new ArrayList<>(Arrays.asList("A Coruña", "Lugo", "Orense", "Pontevedra"));
    public static final ArrayList<String> BALEARES = new ArrayList<>(Arrays.asList("Ibiza", "Mallorca", "Menorca"));
    public static final ArrayList<String> RIOJA = new ArrayList<>(Arrays.asList("La Rioja"));
    public static final ArrayList<String> MElILLA = new ArrayList<>(Arrays.asList("Melilla"));
    public static final ArrayList<String> MURCIA = new ArrayList<>(Arrays.asList("Murcia"));
    public static final ArrayList<String> NAVARA = new ArrayList<>(Arrays.asList("Navarra"));
    public static final ArrayList<String> PAIS_VASCO = new ArrayList<>(Arrays.asList("Álava", "Guipúzcoa", "Vicaya"));

    public static final ArrayList<String> SECTORS = new ArrayList<>(Arrays.asList("Sector", "Tecnológico", "Deportivo", "Ocio", "Servicios", "Educación"));
    public static final ArrayList<String> POSITIONS = new ArrayList<>(Arrays.asList("Cargo", "Jefe", "Subjefe", "Encargado", "Empleado", "Becario"));
    public static final ArrayList<String> DEPARTMENTS = new ArrayList<>(Arrays.asList("Departamento", "Software", "Hardware", "Marketing", "Diseño", "Análisis"));

    public static String getTime(Calendar time) {
        String difference;
        long diff = Calendar.getInstance().getTime().getTime() - time.getTime().getTime();
        double minutes = diff / (1000 * 60);
        if (minutes < 60) {
            difference = minutes + " minutos";
        } else {
            long horas = (long) (minutes / 60);
            if(horas < 24) {
                difference = horas + " horas";
            } else {
                long dias = horas/24;
                difference = dias + " días.";
            }
        }
        return difference;
    }

    public static ArrayList<String> getCities(String state) {
        switch (state) {
            case "Comunidad *":
                return CITIES;
            case "Andalucía":
                return ANDALUCIA;
            case "Aragón":
                return ARAGON;
            case "Asturias":
                return ASTURIAS;
            case "Canarias":
                return CANARIAS;
            case "Cantabria":
                return CANTABRIA;
            case "Castilla y León":
                return CASTILLA_LEON;
            case "Castilla la Mancha":
                return CASTILLA_MANCHA;
            case "Cataluña":
                return CATALUNYA;
            case "Ceuta":
                return CEUTA;
            case "Comunidad Valenciana":
                return COM_VAL;
            case "Comunidad de Madrid":
                return MADRID;
            case "Extremadura":
                return EXTREMADURA;
            case "Galicia":
                return GALICIA;
            case "Islas Baleares":
                return BALEARES;
            case "La Rioja":
                return RIOJA;
            case "Melilla":
                return MElILLA;
            case "Murcia":
                return MURCIA;
            case "Navarra":
                return NAVARA;
            case "País Vasco":
                return PAIS_VASCO;
        }
        return CITIES;
    }

}
