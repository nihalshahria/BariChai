package com.example.VaraBari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private static int splashTime = 3000;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(LogInScreenActivity.prefName, 0);
                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn", false);
                if(hasLoggedIn){
                    Intent homeIntent = new Intent(SplashActivity.this, HomePage.class);
                    startActivity(homeIntent);
                    finish();
                }else {
                    Intent loginIntent = new Intent(SplashActivity.this, LogInScreenActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        }, splashTime);
    }
}