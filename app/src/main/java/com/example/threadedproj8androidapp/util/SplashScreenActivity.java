package com.example.threadedproj8androidapp.util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.threadedproj8androidapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide(); //add this to hide ActionBar

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class); startActivity(i);
                finish(); } }, 2000);
    }
}