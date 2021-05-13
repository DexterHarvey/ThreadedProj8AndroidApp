package com.example.threadedproj8androidapp.util;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.threadedproj8androidapp.managers.CustomerManager;
import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.managers.URLManager;
import com.example.threadedproj8androidapp.model.CustomerEntity;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    TextView lblAttemptInfo;
    TextView lblRegister;
    Switch switchRememberMe;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().hide(); //add this to Hide the ActionBar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        editor = preferences.edit();
        switchRememberMe = findViewById(R.id.switchRememberMe);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        lblAttemptInfo = findViewById(R.id.lblAttemptInfo);
        lblRegister = findViewById(R.id.lblRegister);
        Intent intent = getIntent();
        if (intent.getBooleanExtra("isLogout", false)) {
            editor.remove("USERNAME").commit();
            editor.remove("PASSWORD").commit();
            switchRememberMe.setChecked(false);
        }
        queue = Volley.newRequestQueue(getApplicationContext());
        String savedUsername = preferences.getString("USERNAME", "");
        String savedPassword = preferences.getString("PASSWORD", "");
        if(!savedUsername.equals("") && !savedPassword.equals("")) {
            JsonObjectRequest custRequest = new JsonObjectRequest(Request.Method.GET, URLManager.getLoginURL(savedUsername, savedPassword), null,
                    response -> {
                        if (response.has("customerId"))
                            try {
                                switchRememberMe.setChecked(true);
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
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = txtUsername.getText().toString();
                String inputPassword = txtPassword.getText().toString();

                if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
                    // todo: remove these two lines! just a development speedup
                    txtUsername.setText("lenison");
                    txtPassword.setText("example123");
                } else {
                    if (switchRememberMe.isChecked()) {
                        // Request a string response from the provided URL.
                        JsonObjectRequest custRequest = new JsonObjectRequest(Request.Method.GET, URLManager.getLoginURL(inputUsername, inputPassword), null,
                                response -> {
                                    try {
                                        if (response.has("customerId"))
                                            try {
                                                CustomerEntity customer = CustomerManager.buildCustomer(response);
                                                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                                                intent.putExtra("customer", (Serializable) customer);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                getApplicationContext().startActivity(intent);
                                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                editor.putString("USERNAME", inputUsername);
                                                editor.putString("PASSWORD", inputPassword);
                                                editor.apply();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Invalid Login Credentials. Please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show());
                        queue.start();
                        queue.add(custRequest);
                    } else {
                        JsonObjectRequest custRequest = new JsonObjectRequest(Request.Method.GET, URLManager.getLoginURL(inputUsername, inputPassword), null,
                                response -> {
                                    try {
                                        if (response.has("customerId"))
                                            try {
                                                CustomerEntity customer = CustomerManager.buildCustomer(response);
                                                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                                                intent.putExtra("customer", (Serializable) customer);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                getApplicationContext().startActivity(intent);
                                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Invalid Login Credentials. Please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show());
                        queue.start();
                        queue.add(custRequest);
                    }
                }
            }
        });
        lblRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }
}
