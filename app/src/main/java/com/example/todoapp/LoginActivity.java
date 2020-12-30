package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    EditText eemail, ppassword;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();

        eemail = findViewById(R.id.LoginEmail);
        ppassword = findViewById(R.id.LoginPassword);


        Button btn_Login = findViewById(R.id.Login_btn);
        btn_Login.setOnClickListener(view -> {


            String email = eemail.getText().toString().trim();
            String password = ppassword.getText().toString().trim();
            if (email.isEmpty()) {
                eemail.setError("Please enter email");
                return;
            }
            if (password.isEmpty()) {
                ppassword.setError("Please enter password");
                return;
            }

            btn_Login.setEnabled(false);
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        btn_Login.setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        TextView tv_register = findViewById(R.id.To_SignUp);
        tv_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}