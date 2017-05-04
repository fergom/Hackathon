package com.a480.fernando.hackathon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.a480.fernando.hackathon.model.User;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static com.a480.fernando.hackathon.AppConstant.CITIES;
import static com.a480.fernando.hackathon.AppConstant.COUNTRIES;
import static com.a480.fernando.hackathon.AppConstant.DEFAULT_PROFILE_IMAGE_URL;
import static com.a480.fernando.hackathon.AppConstant.DEPARTMENTS;
import static com.a480.fernando.hackathon.AppConstant.POSITIONS;
import static com.a480.fernando.hackathon.AppConstant.SECTORS;
import static com.a480.fernando.hackathon.AppConstant.STATES;
import static com.a480.fernando.hackathon.AppConstant.getCities;

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

    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private User createdUser;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_form);

        auth = FirebaseAuth.getInstance();

        initFacebook();

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
                                    LoginManager.getInstance().logOut();
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
        Intent intent = new Intent(InscriptionFormActivity.this, SuccessfulRegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                showData(currentProfile, "");
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest mGraphRequest = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    String email = me.optString("email");

                                    showData(Profile.getCurrentProfile(), email);
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                mGraphRequest.setParameters(parameters);
                mGraphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println(error);
            }
        };

        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        facebookLoginButton.registerCallback(callbackManager, callback);
        LoginManager.getInstance().registerCallback(callbackManager, callback);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData(Profile.getCurrentProfile(), "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void showData(Profile profile, String email) {
        if(profile != null) {
            this.profile = profile;
            this.name.setText(profile.getFirstName());
            this.surname.setText(profile.getLastName());
            this.email.setText(email);
        }
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
        user.setNetworking(true);
        CheckBox fact = (CheckBox) findViewById(R.id.inscription_fact);
        user.setFact(fact.isChecked());
        if(profile == null) {
            user.setImage(DEFAULT_PROFILE_IMAGE_URL);
        } else {
            user.setImage(profile.getProfilePictureUri(100,100).toString());
        }
        return user;
    }

}
