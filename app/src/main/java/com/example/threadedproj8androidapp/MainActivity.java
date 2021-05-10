package com.example.threadedproj8androidapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    TextView lblAttemptInfo;
    RequestQueue queue;
    String url = "http://10.0.2.2:8080/RESTApiForAndroid_war_exploded/api/customers/getlogin/lenison/example123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        lblAttemptInfo = findViewById(R.id.lblAttemptInfo);
        queue = Volley.newRequestQueue(getApplicationContext());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = txtUsername.getText().toString();
                String inputPassword = txtPassword.getText().toString();

                if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Request a string response from the provided URL.
                    JsonObjectRequest custRequest = new JsonObjectRequest(Request.Method.GET, URLManager.getLoginURL(inputUsername, inputPassword), null,
                            response -> {
                                try {
                                    if (response.has("customerId"))
                                        try {
                                            CustomerEntity customer = CustomerHandler.buildCustomer(response);
                                            Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                                            intent.putExtra("customer", (Serializable) customer);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            getApplicationContext().startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show());
                    queue.start();
                    queue.add(custRequest);
                }
            }
        });
    }
}
