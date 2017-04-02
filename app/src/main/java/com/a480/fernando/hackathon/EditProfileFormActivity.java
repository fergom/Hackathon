package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class EditProfileFormActivity extends BaseActivity {

    private Spinner countrySpinner;
    private Spinner stateSpinner;
    private Spinner citySpinner;
    private Spinner sectorSpinner;
    private Spinner positionSpinner;
    private Spinner departmentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_form);

        countrySpinner = (Spinner) findViewById(R.id.inscription_country);
        stateSpinner = (Spinner) findViewById(R.id.inscription_state);
        citySpinner = (Spinner) findViewById(R.id.inscription_city);
        sectorSpinner = (Spinner) findViewById(R.id.inscription_sector);
        positionSpinner = (Spinner) findViewById(R.id.inscription_position);
        departmentSpinner = (Spinner) findViewById(R.id.inscription_department);

        List<String> countries = new ArrayList<String>();
        countries.add("Pais");
        countries.add("Francia");
        countries.add("Inglaterra");
        countries.add("España");
        countries.add("Italia");
        countries.add("Alemania");
        countries.add("Polonia");

        List<String> states = new ArrayList<String>();
        states.add("Comunidad");
        states.add("Aragon");
        states.add("Comunidad Valenciana");
        states.add("Madrid");
        states.add("Andalucia");
        states.add("Murcia");
        states.add("Galicia");

        List<String> cities = new ArrayList<String>();
        cities.add("Ciudad");
        cities.add("Castellon de la Plana");
        cities.add("Elche");
        cities.add("Valencia");
        cities.add("Alicante");
        cities.add("Chovar");
        cities.add("Soneja");

        List<String> sectors = new ArrayList<String>();
        sectors.add("Sector");
        sectors.add("Tecnológico");
        sectors.add("Deportivo");
        sectors.add("Ocio");
        sectors.add("Educacion");
        sectors.add("Servicios");

        List<String> positions = new ArrayList<String>();
        positions.add("Cargo");
        positions.add("Jefe");
        positions.add("Subjefe");
        positions.add("Jefe de departamento");
        positions.add("Empleado");
        positions.add("Becario");

        List<String> departments = new ArrayList<String>();
        departments.add("Departamento");
        departments.add("RRHH");
        departments.add("Marketing");
        departments.add("Diseño");
        departments.add("Software");
        departments.add("Bussiness");

        ArrayAdapter<String> yourAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, countries);
        countrySpinner.setAdapter(yourAdapter);
        countrySpinner.setDropDownVerticalOffset(140);
        yourAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, states);
        stateSpinner.setAdapter(yourAdapter);
        stateSpinner.setDropDownVerticalOffset(140);
        yourAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, cities);
        citySpinner.setAdapter(yourAdapter);
        citySpinner.setDropDownVerticalOffset(140);
        yourAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, sectors);
        sectorSpinner.setAdapter(yourAdapter);
        sectorSpinner.setDropDownVerticalOffset(140);
        yourAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, positions);
        positionSpinner.setAdapter(yourAdapter);
        positionSpinner.setDropDownVerticalOffset(140);
        yourAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, departments);
        departmentSpinner.setAdapter(yourAdapter);
        departmentSpinner.setDropDownVerticalOffset(140);

        Bundle b = this.getIntent().getExtras();

        if (b != null) {
            user = b.getParcelable(AppConstant.USER);
        }
    }

    public void update(View view) {
        Intent intent = new Intent(EditProfileFormActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

}
