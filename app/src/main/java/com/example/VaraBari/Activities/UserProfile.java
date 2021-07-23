package com.example.VaraBari.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.VaraBari.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public TextView fullName, email, phoneNo, address, navUserFullName;
    private View headerView;
    private ImageView profileImage, navUserPic;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private String _profileImageLink, _fullName, _phoneNo, _address, _email;
    private String uuid;

    private DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Firebase hooks
        myRef = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uuid = firebaseUser.getUid();
        ////////////////////////

        //Other Hooks
        profileImage = (ImageView)findViewById(R.id.profile_pic);
        fullName = (TextView)findViewById(R.id.name);
        email = (TextView)findViewById(R.id.mail);
        phoneNo = (TextView)findViewById(R.id.phone_number);
        address = (TextView)findViewById(R.id.address);
        ///////////////////////////////////////////////////////////////////

        // Navigation Drawer Hooks
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_dashboard);
        navigationView = (NavigationView) findViewById(R.id.nav_view_dashboard);
        headerView = navigationView.getHeaderView(0);
        navUserPic = (ImageView)headerView.findViewById(R.id.nav_user_pic);
        navUserFullName = (TextView)headerView.findViewById(R.id.nav_user_full_name);
        ///////////////////////////////////////////////////////////////////

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ///////////////////////////////////////////////////////////////////

        // Navigation drawer
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ///////////////////////////////////////////////////////////////////

        // showUserData();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _fullName = dataSnapshot.child(uuid).child("fullName").getValue(String.class);
                _phoneNo = dataSnapshot.child(uuid).child("phoneNo").getValue(String.class);
                _address = dataSnapshot.child(uuid).child("address").getValue(String.class);
                _email = dataSnapshot.child(uuid).child("email").getValue(String.class);
                _profileImageLink = dataSnapshot.child(uuid).child("profileImageLink").getValue(String.class);

                if(!_profileImageLink.isEmpty()){
                    Picasso.get().load(_profileImageLink).into(profileImage);
                }
                if(!_profileImageLink.isEmpty()) {
                    Picasso.get().load(_profileImageLink).into(navUserPic);
                }
                if(!_fullName.isEmpty())navUserFullName.setText(_fullName);
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
        ///////////////////////////////////////////////////////////////////
    }


    // Right corner menu
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inFlater = getMenuInflater();
        inFlater.inflate(R.menu.user_profile_corner_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_user_info:
                startActivity(new Intent(getApplicationContext(), EditUserInfo.class));
                return true;

//               Change Password
            case R.id.change_password:
                Context context = UserProfile.this;
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText currentPassword = new EditText(context);
                currentPassword.setHint("Current Password");
                currentPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                layout.addView(currentPassword);

                final EditText changePassword = new EditText(context);
                changePassword.setHint("New Password");
                changePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                layout.addView(changePassword);

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(context);
                passwordResetDialog.setTitle("Change Password?");
                passwordResetDialog.setView(layout);

                passwordResetDialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String oldPassword = currentPassword.getText().toString();
                                String newPassword = changePassword.getText().toString();
                                if(oldPassword.isEmpty()){
                                    Toast.makeText(context, "Enter current password", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(newPassword.isEmpty()){
                                    Toast.makeText(context, "Enter new password", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), oldPassword);
                                firebaseUser.reauthenticate(authCredential)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                firebaseUser.updatePassword(newPassword)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(UserProfile.this, "Password changed", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull @NotNull Exception e) {
                                                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                passwordResetDialog.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                passwordResetDialog.create().show();
                return true;
            ///////////////////////////////////////////////////////////////////
            case R.id.exit_from_profile:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    ///////////////////////////////////////////////////////////////////

    // Navigation Drawer functionality
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                Intent intent = new Intent(UserProfile.this, DashBoard.class);
                startActivity(intent);
                break;
            case R.id.nav_favourites:
//                Go to the list of favourites
                break;
            case R.id.nav_ads:
//                Go to the list of ads published by user
                break;
            case R.id.nav_profile:
                Intent profile = new Intent(UserProfile.this, UserProfile.class);
                startActivity(profile);
                break;
            case R.id.nav_publish:
                Intent newAd = new Intent(UserProfile.this, NewAdForm.class);
                startActivity(newAd);
                break;
            case R.id.nav_help:
//                Go to user-manual page
                break;
            case R.id.nav_log_out:
                firebaseAuth.signOut();
                SharedPreferences sharedPreferences = getSharedPreferences(LogInScreenActivity.prefName, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.commit();
                startActivity(new Intent(getApplicationContext(), LogInScreenActivity.class));
                break;
        }
        return true;
    }
    ///////////////////////////////////////////////////////////////////
}