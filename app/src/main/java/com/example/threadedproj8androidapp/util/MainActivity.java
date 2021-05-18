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

/**
 * Login activity (originally the main page, prior to splash inclusion). Allows user to login with correct credentials, or click to register.
 * Code by Dexter, easy-debugging functionality and refactoring of code to include splash by Eric.
 * Brand styling by Jetlyn.
 */

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up control refs
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        lblAttemptInfo = findViewById(R.id.lblAttemptInfo);
        lblRegister = findViewById(R.id.lblRegister);
        preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        editor = preferences.edit();
        switchRememberMe = findViewById(R.id.switchRememberMe);
        getSupportActionBar().hide(); //add this to hide ActionBar

        // Set if credentials are present in inputs based on whether user coming from login command or not
        Intent intent = getIntent();
        if (intent.getBooleanExtra("isLogout", false)) {
            editor.remove("USERNAME").commit();
            editor.remove("PASSWORD").commit();
            switchRememberMe.setChecked(false);
        } else if (intent.getStringExtra("newCustUsername") != null && intent.getStringExtra("newCustPassword") != null) {
            txtUsername.setText(intent.getStringExtra("newCustUsername"));
            txtPassword.setText(intent.getStringExtra("newCustPassword"));
        }

        // On login button click, make http request to confirm credentials and log in if valid
        queue = Volley.newRequestQueue(getApplicationContext());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = txtUsername.getText().toString();
                String inputPassword = txtPassword.getText().toString();

                if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
                    // todo: remove these two lines before production! just a development speedup
                    txtUsername.setText("lenison");
                    txtPassword.setText("example123");
                } else {
                    if (switchRememberMe.isChecked()) { // Remember me switch can store credentials in sharedprefs
                        // Request a string response from the provided URL.
                        JsonObjectRequest custRequest = new JsonObjectRequest(Request.Method.GET, URLManager.getLoginURL(inputUsername, inputPassword), null,
                                response -> {
                                    try {
                                        if (response.has("customerId")) {
                                            editor.putString("USERNAME", inputUsername);
                                            editor.putString("PASSWORD", inputPassword);
                                            editor.apply();
                                            getCustAndLogIn(response);
                                        }else {
                                            Toast.makeText(getApplicationContext(), "Invalid Login Credentials. Please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show());
                        queue.start();
                        queue.add(custRequest);
                    } else { // if remember me is not pressed, just
                        JsonObjectRequest custRequest = new JsonObjectRequest(Request.Method.GET, URLManager.getLoginURL(inputUsername, inputPassword), null,
                                response -> {
                                    try {
                                        if (response.has("customerId"))
                                            getCustAndLogIn(response);
                                        else {
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

        // On register link press, go to register activity
        lblRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    // Puts customer info into intent and goes to nav activity
    private void getCustAndLogIn(org.json.JSONObject response) throws JSONException {
        CustomerEntity customer = CustomerManager.buildCustomer(response);
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        intent.putExtra("customer", customer);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
