package com.example.threadedproj8androidapp.util;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                    // todo: remove these two lines! just a development speedup
                    txtUsername.setText("lenison");
                    txtPassword.setText("example123");
                } else {
                    // Request a string response from the provided URL.
                    JsonObjectRequest custRequest = new JsonObjectRequest(Request.Method.GET, URLManager.getLoginURL(inputUsername, inputPassword), null,
                            response -> {
                                try {
                                    if (response.has("customerId"))
                                        try {
                                            CustomerEntity customer = CustomerManager.buildCustomer(response);
                                            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
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
