package com.example.threadedproj8androidapp.util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.model.CustomerEntity;

import java.io.Serializable;

/**
 * Central hub activity which user is greeted by after login.
 * Code and final layout by Eric.
 * Brand colouring, button design and rough layout by Jetlyn.
 */

public class NavigationActivity extends AppCompatActivity {

    ImageView btnToMap;
    ImageView btnToCustInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        getSupportActionBar().hide(); //add this to hide ActionBar
        btnToCustInfo = findViewById(R.id.btnToLogin);
        btnToMap = findViewById(R.id.btnToMap);

        // Get customer info passed from login page
        Intent intent = getIntent();
        CustomerEntity customer = (CustomerEntity) intent.getSerializableExtra("customer");

        // On clicking customer info button, go to that activity with cust in intent
        btnToCustInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                intent.putExtra("customer", (Serializable) customer);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        // On clicking map button, go to that activity
        btnToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PackagesActivity.class);
                intent.putExtra("customer", customer);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}