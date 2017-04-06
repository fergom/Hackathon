package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.a480.fernando.hackathon.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class InscriptionFormActivity extends BaseActivity {

    private Spinner countrySpinner;
    private Spinner stateSpinner;
    private Spinner citySpinner;
    private Spinner sectorSpinner;
    private Spinner positionSpinner;
    private Spinner departmentSpinner;

    private EditText password;
    private EditText passwordConfirm;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_form);

        auth = FirebaseAuth.getInstance();

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

    }

    public void acceptInscription(View view) {
        password = (EditText) findViewById(R.id.inscription_password);
        passwordConfirm = (EditText) findViewById(R.id.inscription_password_confirm);
        if(!password.getText().toString().equals(passwordConfirm.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show();
        } else if(password.getText().toString().length() < 8) {
            Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 8 caracteres.", Toast.LENGTH_LONG).show();
        } else {
            User createdUser = createUser();
            auth.createUserWithEmailAndPassword(createdUser.getEmail(), createdUser.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(InscriptionFormActivity.this, "No se ha podido registrar el usuario, pruebe en unos minutos.", Toast.LENGTH_SHORT).show();
                        } else {
                            // save user in ddbb
                            AppSharedPreferences.setUser(getApplicationContext(), createdUser.getEmail());
                            startActivity(new Intent(InscriptionFormActivity.this, SuccessfulRegistrationActivity.class));
                        }
                    }
                });
        }
    }

    private User createUser() {
        User user = new User();
        EditText email = (EditText) findViewById(R.id.inscription_email);
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        EditText name = (EditText) findViewById(R.id.inscription_name);
        user.setName(name.getText().toString());
        EditText surname = (EditText) findViewById(R.id.inscription_surname);
        user.setSurname(surname.getText().toString());
        Spinner country = (Spinner) findViewById(R.id.inscription_country);
        user.setCountry(country.getSelectedItem().toString());
        Spinner state = (Spinner) findViewById(R.id.inscription_state);
        user.setState(state.getSelectedItem().toString());
        Spinner city = (Spinner) findViewById(R.id.inscription_city);
        user.setCity(city.getSelectedItem().toString());
        EditText postalCode = (EditText) findViewById(R.id.inscription_cp);
        user.setPostalCode(postalCode.getText().toString());
        EditText phoneNumber = (EditText) findViewById(R.id.inscription_phone);
        user.setPhoneNumber(phoneNumber.getText().toString());
        EditText website = (EditText) findViewById(R.id.inscription_website);
        user.setWebsite(website.getText().toString());
        EditText companyName = (EditText) findViewById(R.id.inscription_company);
        user.setCompanyName(companyName.getText().toString());
        EditText nif = (EditText) findViewById(R.id.inscription_nif);
        user.setNif(nif.getText().toString());
        Spinner sector = (Spinner) findViewById(R.id.inscription_sector);
        user.setSector(sector.getSelectedItem().toString());
        Spinner position = (Spinner) findViewById(R.id.inscription_position);
        user.setPosition(position.getSelectedItem().toString());
        Spinner department = (Spinner) findViewById(R.id.inscription_department);
        user.setDepartment(department.getSelectedItem().toString());
        CheckBox fact = (CheckBox) findViewById(R.id.inscription_fact);
        user.setFact(fact.isChecked());
        return user;
    }

}
