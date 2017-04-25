package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditProfileFormActivity extends BaseActivity {

    private EditText name;
    private EditText surname;
    private EditText postalCode;
    private EditText phoneNumber;
    private EditText nif;

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

        name = (EditText) findViewById(R.id.inscription_name);
        surname = (EditText) findViewById(R.id.inscription_surname);
        postalCode = (EditText) findViewById(R.id.inscription_cp);
        phoneNumber = (EditText) findViewById(R.id.inscription_phone);
        nif = (EditText) findViewById(R.id.inscription_nif);

        name.setText(user.getName());
        surname.setText(user.getSurname());
        postalCode.setText(user.getPostalCode());
        phoneNumber.setText(user.getPhoneNumber());
        nif.setText(user.getNif());

        countrySpinner = (Spinner) findViewById(R.id.inscription_country);
        stateSpinner = (Spinner) findViewById(R.id.inscription_state);
        citySpinner = (Spinner) findViewById(R.id.inscription_city);
        sectorSpinner = (Spinner) findViewById(R.id.inscription_sector);
        positionSpinner = (Spinner) findViewById(R.id.inscription_position);
        departmentSpinner = (Spinner) findViewById(R.id.inscription_department);

        List<String> countries = new ArrayList<String>();
        countries.add("Pais *");
        countries.add("España");

        List<String> states = new ArrayList<String>();
        states.add("Comunidad *");
        states.add("Aragón");
        states.add("Comunidad Valenciana");
        states.add("Madrid");
        states.add("Andalucía");
        states.add("Murcia");
        states.add("Galicia");

        List<String> cities = new ArrayList<String>();
        cities.add("Ciudad *");
        cities.add("Castellón de la Plana");
        cities.add("Elche");
        cities.add("Valencia");
        cities.add("Alicante");

        List<String> sectors = new ArrayList<String>();
        sectors.add("Sector");
        sectors.add("Tecnológico");
        sectors.add("Deportivo");
        sectors.add("Ocio");
        sectors.add("Educación");
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

    }

    public void update(View view) {
        if(name.getText().toString().length() == 0 ||
                surname.getText().toString().length() == 0 ||
                postalCode.getText().toString().length() == 0 ||
                phoneNumber.getText().toString().length() == 0 ||
                nif.getText().toString().length() == 0 ||
                countrySpinner.getSelectedItem().toString().equals("Pais *") ||
                stateSpinner.getSelectedItem().toString().equals("Comunidad *") ||
                citySpinner.getSelectedItem().toString().equals("Ciudad *")) {

            Toast.makeText(getApplicationContext(), "Los campos con * son obligatorios.", Toast.LENGTH_LONG).show();

        } else {
            updateUser();
            userDao.saveUser(user);
            Intent intent = new Intent(EditProfileFormActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }

    private void updateUser() {
        user.setName(name.getText().toString());
        user.setSurname(surname.getText().toString());
        user.setCountry(countrySpinner.getSelectedItem().toString());
        user.setState(stateSpinner.getSelectedItem().toString());
        user.setCity(citySpinner.getSelectedItem().toString());
        user.setPostalCode(postalCode.getText().toString());
        user.setPhoneNumber(phoneNumber.getText().toString());
        EditText website = (EditText) findViewById(R.id.inscription_website);
        user.setWebsite(website.getText().toString());
        EditText companyName = (EditText) findViewById(R.id.inscription_company);
        user.setCompanyName(companyName.getText().toString());
        user.setNif(nif.getText().toString());
        user.setSector(sectorSpinner.getSelectedItem().toString());
        user.setPosition(positionSpinner.getSelectedItem().toString());
        user.setDepartment(departmentSpinner.getSelectedItem().toString());
        CheckBox fact = (CheckBox) findViewById(R.id.inscription_fact);
        user.setFact(fact.isChecked());
    }

}
