package com.example.threadedproj8androidapp.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.threadedproj8androidapp.R;

import java.io.Serializable;

public class NavigationActivity extends AppCompatActivity {

    ImageView btnToMap;
    ImageView btnToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        getSupportActionBar().hide(); //add this to hide ActionBar

        // Get customer info passed from login page
        Intent intent = getIntent();
        com.example.threadedproj8androidapp.model.CustomerEntity customer = (com.example.threadedproj8androidapp.model.CustomerEntity) intent.getSerializableExtra("customer");

        btnToLogin = findViewById(R.id.btnToLogin);
        btnToMap = findViewById(R.id.btnToMap);

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                intent.putExtra("customer", (Serializable) customer);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
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