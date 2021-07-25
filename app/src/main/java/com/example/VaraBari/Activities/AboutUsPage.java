package com.example.VaraBari.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.VaraBari.R;

public class AboutUsPage extends AppCompatActivity {

    Toolbar toolbar;
    private Animation animationL2R, animationR2L, animationU2D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        animationL2R = AnimationUtils.loadAnimation(AboutUsPage.this, R.anim.lefttoright);
        findViewById(R.id.about_us_jowher).startAnimation(animationL2R);
        animationR2L = AnimationUtils.loadAnimation(AboutUsPage.this, R.anim.righttoleft);
        findViewById(R.id.about_us_nihal).startAnimation(animationR2L);
        animationU2D = AnimationUtils.loadAnimation(AboutUsPage.this, R.anim.uptodown);
        findViewById(R.id.about_us_title_text).startAnimation(animationU2D);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}