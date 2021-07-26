package com.example.VaraBari.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.VaraBari.R;

public class AboutUsPage extends AppCompatActivity {

    Toolbar toolbar;
    private Animation animationL2R, animationR2L, animationU2D;
    private TextView nihalPhone, jowherPhone;
    private LinearLayoutCompat nihalGit, jowherGit, nihalFb, jowherFb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        animationL2R = AnimationUtils.loadAnimation(AboutUsPage.this, R.anim.lefttoright);
        findViewById(R.id.about_us_jowher).startAnimation(animationL2R);
        animationR2L = AnimationUtils.loadAnimation(AboutUsPage.this, R.anim.righttoleft);
        findViewById(R.id.about_us_nihal).startAnimation(animationR2L);
        animationU2D = AnimationUtils.loadAnimation(AboutUsPage.this, R.anim.uptodown);
        findViewById(R.id.about_us_title_text).startAnimation(animationU2D);

        nihalPhone = findViewById(R.id.about_us_phone_nihal);
        jowherPhone = findViewById(R.id.about_us_phone_jowher);
        nihalGit = findViewById(R.id.about_us_github_nihal);
        jowherGit = findViewById(R.id.about_us_github_jowher);
        nihalFb = findViewById(R.id.about_us_facebook_nihal);
        jowherFb = findViewById(R.id.about_us_facebook_jowher);

        nihalPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "tel:"+"01646767354";
                Toast.makeText(AboutUsPage.this, "Calling", Toast.LENGTH_SHORT).show();
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse(number));
                startActivity(call);
            }
        });

        jowherPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "tel:"+"01756633603";
                Toast.makeText(AboutUsPage.this, "Calling", Toast.LENGTH_SHORT).show();
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse(number));
                startActivity(call);
            }
        });

        nihalGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.nihal_github_link)));
                startActivity(browserIntent);
            }
        });

        jowherGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.jowher_github_link)));
                startActivity(browserIntent);
            }
        });

        nihalFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.nihal_facebook_link)));
                startActivity(browserIntent);
            }
        });

        jowherFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.jowher_facebook_link)));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}