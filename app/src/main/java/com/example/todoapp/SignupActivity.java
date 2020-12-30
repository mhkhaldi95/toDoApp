package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private TextView toLoginTxt;

    EditText eemail, nname, ppassword;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_signup);


        firebaseAuth = FirebaseAuth.getInstance();
        eemail = findViewById(R.id.RegisterEmail);
        nname = findViewById(R.id.RegisterName);
        ppassword = findViewById(R.id.RegisterPassword);


        Button btn_Register = findViewById(R.id.Register_btn);
        btn_Register.setOnClickListener(view -> {


            String email = eemail.getText().toString().trim();
            String name = nname.getText().toString().trim();
            String password = ppassword.getText().toString().trim();
            if (email.isEmpty()) {
                eemail.setError("Please enter email");
                return;
            }
            if (name.isEmpty()) {
                nname.setError("Please enter name");
                return;
            }
            if (password.isEmpty()) {
                ppassword.setError("Please enter password");
                return;
            }

            btn_Register.setEnabled(false);
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {

                        btn_Register.setEnabled(true);
                        if (task.isSuccessful()) {

                            currentUser = firebaseAuth.getCurrentUser();
                            String uid = currentUser.getUid();
                            Map<String, Object> data = new HashMap<>();
                            data.put("uid", uid);
                            data.put("name", name);
                            FirebaseDatabase.getInstance().getReference("Users").child(uid).setValue(data)
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(SignupActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    });

                        } else {
                            Toast.makeText(SignupActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        TextView tv_login = findViewById(R.id.To_login);
        tv_login.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


}