package com.example.VaraBari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Form extends AppCompatActivity {
    private EditText textFullName, textUsername, textEmail, textPassword, textConfirmPassword;
    private CardView signUp;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        textFullName = (EditText) findViewById(R.id.full_name);
        textUsername = (EditText) findViewById(R.id.username);
        textEmail = (EditText) findViewById(R.id.email);
        textPassword = (EditText) findViewById(R.id.password);
        textConfirmPassword = (EditText)findViewById(R.id.confirmed_password);
        signUp = (CardView) findViewById(R.id.signup);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        firebaseAuth = firebaseAuth.getInstance();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = textFullName.getText().toString().trim();
                String username = textUsername.getText().toString().trim();
                String email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();

                String confirmedPassword = textConfirmPassword.getText().toString().trim();
//                Toast.makeText(Signup_Form.this, fullname, Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(fullname)) {
                    textFullName.setError("Full name is required");
                    textFullName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    textUsername.setError("Username is required");
                    textUsername.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    textEmail.setError("Email is required");
                    textEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    textEmail.setError("Please provide valid email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    textPassword.setError("Password is required");
                    textPassword.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(confirmedPassword)) {
                    textConfirmPassword.setError("Confirmed Password is required");
                    textConfirmPassword.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                if (password.equals(confirmedPassword)) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        User user = new User(fullname, username, email, password);
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(Signup_Form.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                }else{
                                                    Toast.makeText(Signup_Form.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                                    }else{
                                        Toast.makeText(Signup_Form.this, "Registration is failed", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                }else{
                    Toast.makeText(Signup_Form.this, "Passwords didn't match", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}

// Debug token nihal 65A82F97-4B8D-4D17-B89A-04D5CD5748D4