package com.example.VaraBari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class UserProfile extends AppCompatActivity {
    public TextView fullName, email, phoneNo, address;
    private String _fullName, _phoneNo, _address, _email;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        myRef = FirebaseDatabase.getInstance().getReference("Users");

        fullName = (TextView)findViewById(R.id.name);
        email = (TextView)findViewById(R.id.mail);
        phoneNo = (TextView)findViewById(R.id.phone_number);
        address = (TextView)findViewById(R.id.address);


        // showUserData();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _fullName = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("fullName").getValue(String.class);
                _phoneNo = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("phoneNo").getValue(String.class);
                _address = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address").getValue(String.class);
                _email = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").getValue(String.class);

                if(!_fullName.isEmpty())fullName.setText(_fullName);
                if(!_email.isEmpty())email.setText(_email);
                if(!_phoneNo.isEmpty())phoneNo.setText(_phoneNo);
                if(!_address.isEmpty())address.setText(_address);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Error!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inFlater = getMenuInflater();
        inFlater.inflate(R.menu.user_profile_corber_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_user_info:
                startActivity(new Intent(getApplicationContext(), EditUserInfo.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}