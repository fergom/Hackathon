package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.a480.fernando.hackathon.AppConstant.CITIES;
import static com.a480.fernando.hackathon.AppConstant.COUNTRIES;
import static com.a480.fernando.hackathon.AppConstant.DEPARTMENTS;
import static com.a480.fernando.hackathon.AppConstant.POSITIONS;
import static com.a480.fernando.hackathon.AppConstant.SECTORS;
import static com.a480.fernando.hackathon.AppConstant.STATES;
import static com.a480.fernando.hackathon.AppConstant.getCities;

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

        initSpinner(COUNTRIES, countrySpinner);
        initSpinner(STATES, stateSpinner);
        initSpinner(CITIES, citySpinner);
        initSpinner(SECTORS, sectorSpinner);
        initSpinner(POSITIONS, positionSpinner);
        initSpinner(DEPARTMENTS, departmentSpinner);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initSpinner(getCities(STATES.get(position)), citySpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void initSpinner(ArrayList<String> data, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, data);
        spinner.setAdapter(adapter);
        spinner.setDropDownVerticalOffset(140);
    }

    public void update(View view) {
        if(name.getText().toString().length() == 0 ||
                surname.getText().toString().length() == 0 ||
                postalCode.getText().toString().length() == 0 ||
                phoneNumber.getText().toString().length() == 0 ||
                nif.getText().toString().length() == 0 ||
                countrySpinner.getSelectedItem().toString().equals("País *") ||
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
