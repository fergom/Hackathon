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

public class LoginActivity extends BaseActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

    }

    public void loginCredentials(View view) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_progress_bar);
        LinearLayout loginInfoLayout = (LinearLayout) findViewById(R.id.login_info_layout);
        EditText emailEditText = (EditText) findViewById(R.id.login_email);
        EditText passwordEditText = (EditText) findViewById(R.id.login_password);
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "El email es un campo obligatorio.", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "La contraseña es un campo obligatorio.", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(LoginActivity.this, "Email o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                        } else {

                            /*FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference rootRef = database.getReference("");
                            DatabaseReference userRef = rootRef.child("Users/" + mAuth.getCurrentUser().getUid());

                            userRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Log.d("FIREBASE", dataSnapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Log.w("FIREBASE", "Failed to read value.", error.toException());
                                }
                            });*/
                            userDao.login();
                            AppSharedPreferences.setUser(getApplicationContext(), email);
                            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                        }
                    }
                });
        }

    }
}
