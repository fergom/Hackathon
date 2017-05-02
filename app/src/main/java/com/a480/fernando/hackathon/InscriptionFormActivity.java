package com.a480.fernando.hackathon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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

public class InscriptionFormActivity extends BaseActivity implements ICallbackActivity {

    private EditText email;
    private EditText password;
    private EditText passwordConfirm;
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

    private FirebaseAuth auth;

    private User createdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_form);

        auth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.inscription_email);
        name = (EditText) findViewById(R.id.inscription_name);
        surname = (EditText) findViewById(R.id.inscription_surname);
        postalCode = (EditText) findViewById(R.id.inscription_cp);
        phoneNumber = (EditText) findViewById(R.id.inscription_phone);
        nif = (EditText) findViewById(R.id.inscription_nif);
        password = (EditText) findViewById(R.id.inscription_password);
        passwordConfirm = (EditText) findViewById(R.id.inscription_password_confirm);

        countrySpinner = (Spinner) findViewById(R.id.inscription_country);
        stateSpinner = (Spinner) findViewById(R.id.inscription_state);
        citySpinner = (Spinner) findViewById(R.id.inscription_city);
        sectorSpinner = (Spinner) findViewById(R.id.inscription_sector);
        positionSpinner = (Spinner) findViewById(R.id.inscription_position);
        departmentSpinner = (Spinner) findViewById(R.id.inscription_department);

        List<String> countries = new ArrayList<String>();
        countries.add("País *");
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
        cities.add("Chóvar");
        cities.add("Soneja");

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

    public void linkedinInscription(View view) {

    }

    public void acceptInscription(View view) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else if(email.getText().toString().length() == 0 ||
                password.getText().toString().length() == 0 ||
                passwordConfirm.getText().toString().length() == 0 ||
                name.getText().toString().length() == 0 ||
                surname.getText().toString().length() == 0 ||
                postalCode.getText().toString().length() == 0 ||
                phoneNumber.getText().toString().length() == 0 ||
                nif.getText().toString().length() == 0 ||
                countrySpinner.getSelectedItem().toString().equals("País *") ||
                stateSpinner.getSelectedItem().toString().equals("Comunidad *") ||
                citySpinner.getSelectedItem().toString().equals("Ciudad *")) {

            Toast.makeText(getApplicationContext(), "Los campos con * son obligatorios.", Toast.LENGTH_LONG).show();

        } else {
            if(!password.getText().toString().equals(passwordConfirm.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show();
            } else if(password.getText().toString().length() < 8) {
                Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 8 caracteres.", Toast.LENGTH_LONG).show();
            } else {
                createdUser = createUser();
                auth.createUserWithEmailAndPassword(createdUser.getEmail(), createdUser.getPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(InscriptionFormActivity.this, "No se ha podido registrar el usuario, pruebe en unos minutos.", Toast.LENGTH_SHORT).show();
                            } else {
                                userDao.setCallback(InscriptionFormActivity.this);
                                userDao.onAuthenticated();
                                userDao.saveUser(createdUser);
                            }
                        }
                    });
            }
        }
    }

    public void onDataLoaded() {
        startActivity(new Intent(InscriptionFormActivity.this, SuccessfulRegistrationActivity.class));
        finish();
    }

    private User createUser() {
        User user = new User();
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
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
        user.setImage("https://firebasestorage.googleapis.com/v0/b/hackathon-4d513.appspot.com/o/profile.png?alt=media&token=3e4335fc-5095-402a-b751-06fd85108805");
        return user;
    }

}
