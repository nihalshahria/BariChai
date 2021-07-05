package com.example.VaraBari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {
    private CardView logOut;
    private TextView currentUserEmail;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        currentUserEmail = (TextView)findViewById(R.id.curren_useremail);
        currentUserEmail.setText(firebaseUser.getEmail());
        logOut = (CardView)findViewById(R.id.logout_button);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                SharedPreferences sharedPreferences = getSharedPreferences(LogInScreenActivity.prefName, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                startActivity(new Intent(getApplicationContext(), LogInScreenActivity.class));
                finish();

            }
        });

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if(menu instanceof MenuBuilder){
//            ((MenuBuilder)menu).setOptionalIconsVisible(true);
//        }
//        MenuInflater inFlater = getMenuInflater();
//        inFlater.inflate(R.menu.top_right_corner_menu, menu);
//        return super.onCreateOptionsMenu(menu);
        MenuInflater inFlater = getMenuInflater();
        inFlater.inflate(R.menu.top_right_corner_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item01:
                Toast.makeText(this, "Item 01 was clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                firebaseAuth.signOut();
                SharedPreferences sharedPreferences = getSharedPreferences(LogInScreenActivity.prefName, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                startActivity(new Intent(getApplicationContext(), LogInScreenActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}