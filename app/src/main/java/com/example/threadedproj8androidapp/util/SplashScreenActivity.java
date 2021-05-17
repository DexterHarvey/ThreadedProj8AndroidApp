package com.example.threadedproj8androidapp.util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.managers.CustomerManager;
import com.example.threadedproj8androidapp.managers.URLManager;
import com.example.threadedproj8androidapp.model.CustomerEntity;

import org.json.JSONException;

import java.io.Serializable;

/**
 * Splash screen to serve as initial logo. Room for loading in future if needed.
 * Created by Jetlyn.
 * Some refactoring done by Eric.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide(); //add this to hide ActionBar

        preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        editor = preferences.edit();
        Intent intent = getIntent();
        queue = Volley.newRequestQueue(getApplicationContext());

        if (intent.getBooleanExtra("isLogout", false)) {
            editor.remove("USERNAME").commit();
            editor.remove("PASSWORD").commit();
        }
        String savedUsername = preferences.getString("USERNAME", "");
        String savedPassword = preferences.getString("PASSWORD", "");


            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (!savedUsername.equals("") && !savedPassword.equals("")) {
                        JsonObjectRequest custRequest = new JsonObjectRequest(Request.Method.GET, URLManager.getLoginURL(savedUsername, savedPassword), null,
                                response -> {
                                    if (response.has("customerId"))
                                        try {
                                            CustomerEntity customer = CustomerManager.buildCustomer(response);
                                            Intent intent1 = new Intent(getApplicationContext(), NavigationActivity.class);
                                            intent1.putExtra("customer", (Serializable) customer);
                                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            getApplicationContext().startActivity(intent1);
                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show());
                        queue.start();
                        queue.add(custRequest);
                    } else{

                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    }

                }
            }, 2000);
        }
}