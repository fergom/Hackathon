package com.a480.fernando.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends BaseActivity implements ICallbackActivity {

    private FirebaseAuth mAuth;
    private static String email;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
    }

    public void loginCredentials(View view) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_progress_bar);
        LinearLayout loginInfoLayout = (LinearLayout) findViewById(R.id.login_info_layout);
        emailEditText = (EditText) findViewById(R.id.login_email);
        EditText passwordEditText = (EditText) findViewById(R.id.login_password);
        email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "El email es un campo obligatorio.", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "La contrase�a es un campo obligatorio.", Toast.LENGTH_SHORT).show();
        } else {
            loginInfoLayout.setAlpha(0.3f);
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        loginInfoLayout.setAlpha(1);
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Email o contrase�a incorrectos.", Toast.LENGTH_SHORT).show();
                        } else {
                            setToken();
                            userDao.setCallback(LoginActivity.this);
                            userDao.onAuthenticated();
                        }
                    }
                });
        }
    }

    public void onDataLoaded() {
        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
    }

    public void forgotPassword(View view) {
        if(emailEditText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Introduzca su email.", Toast.LENGTH_LONG).show();
        } else {
            mAuth.sendPasswordResetEmail(emailEditText.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Se ha enviado un email a la direcci�n de correo. Cambie su contrase�a y despu�s haga login con la nueva.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }
    }

}
