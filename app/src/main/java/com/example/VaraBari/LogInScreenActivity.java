package com.example.VaraBari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogInScreenActivity extends AppCompatActivity {
    private EditText loginUsername, loginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        loginUsername = (EditText)findViewById(R.id.login_username);
        loginPassword = (EditText)findViewById(R.id.login_password);
    }

    public void button_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(), Signup_Form.class));
    }

    public void logIn(View view) {
        loginUsername = (EditText)findViewById(R.id.login_username);
        loginPassword = (EditText)findViewById(R.id.login_password);
        String username = loginUsername.getText().toString().trim();
        Toast.makeText(LogInScreenActivity.this, username, Toast.LENGTH_SHORT).show();
    }
}