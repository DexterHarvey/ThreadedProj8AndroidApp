package com.example.threadedproj8androidapp.util;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.managers.CustomerManager;
import com.example.threadedproj8androidapp.managers.URLManager;
import com.example.threadedproj8androidapp.model.CustomerEntity;

import org.json.JSONObject;

/**
 * Registration page to allow new user to be created.
 * All code by Dexter.
 */

public class RegisterActivity extends AppCompatActivity {
    EditText txtCustFNameReg;
    EditText txtCustLNameReg;
    EditText txtCustAddressReg;
    EditText txtCustCityReg;
    EditText txtCustProvinceReg;
    EditText txtCustPostalCodeReg;
    EditText txtCustCountryReg;
    EditText txtCustHomePhoneReg;
    EditText txtCustBusPhoneReg;
    EditText txtCustEmailReg;
    EditText txtUsernameReg;
    EditText txtPasswordReg;
    Button btnRegisterCustomer;
    RequestQueue queue;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtCustFNameReg = findViewById(R.id.txtCustFNameReg);
        txtCustLNameReg = findViewById(R.id.txtCustLNameReg);
        txtCustAddressReg = findViewById(R.id.txtCustAddressReg);
        txtCustCityReg = findViewById(R.id.txtCustCityReg);
        txtCustProvinceReg = findViewById(R.id.txtCustProvinceReg);
        txtCustPostalCodeReg = findViewById(R.id.txtCustPostalCodeReg);
        txtCustCountryReg = findViewById(R.id.txtCustCountryReg);
        txtCustHomePhoneReg = findViewById(R.id.txtCustHomePhoneReg);
        txtCustBusPhoneReg = findViewById(R.id.txtCustBusPhoneReg);
        txtCustEmailReg = findViewById(R.id.txtCustEmailReg);
        txtUsernameReg = findViewById(R.id.txtUsernameReg);
        txtPasswordReg = findViewById(R.id.txtPasswordReg);
        btnRegisterCustomer = findViewById(R.id.btnRegisterCustomer);
        CustomerEntity customer = new CustomerEntity();
        btnRegisterCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (txtCustFNameReg.getText().toString().equals("")) {
                    txtCustFNameReg.setText("John");
                    txtCustLNameReg.setText("Guy");
                    txtCustAddressReg.setText("123 Example Street");
                    txtCustCityReg.setText("Calgary");
                    txtCustProvinceReg.setText("AB");
                    txtCustPostalCodeReg.setText("T4S0C7");
                    txtCustCountryReg.setText("Canada");
                    txtCustHomePhoneReg.setText("123-456-7890");
                    txtCustBusPhoneReg.setText("567-890-1234");
                    txtCustEmailReg.setText("johnguy@exmaple.com");
                    txtUsernameReg.setText("jguy");
                    txtPasswordReg.setText("password");
                }
                else {
                    customer.setCustFirstName(txtCustFNameReg.getText().toString());
                    customer.setCustLastName(txtCustLNameReg.getText().toString());
                    customer.setCustAddress(txtCustAddressReg.getText().toString());
                    customer.setCustCity(txtCustCityReg.getText().toString());
                    customer.setCustProv(txtCustProvinceReg.getText().toString());
                    customer.setCustPostal(txtCustPostalCodeReg.getText().toString());
                    customer.setCustCountry(txtCustCountryReg.getText().toString());
                    customer.setCustHomePhone(PhoneNumberUtils.formatNumber(txtCustHomePhoneReg.getText().toString()));
                    customer.setCustBusPhone(PhoneNumberUtils.formatNumber(txtCustBusPhoneReg.getText().toString()));
                    customer.setCustEmail(txtCustEmailReg.getText().toString());
                    customer.setUsername(txtUsernameReg.getText().toString());
                    customer.setPassword(txtPasswordReg.getText().toString());
                    customer.setAgentId(1);
                    JSONObject customerJSON = CustomerManager.buildJSONFromCustomer(customer);
                    queue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest customerPost = new JsonObjectRequest(Request.Method.POST, URLManager.getCustPostURL(), customerJSON,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("newCustUsername", customer.getUsername());
                                    intent.putExtra("newCustPassword", customer.getPassword());
                                    getApplicationContext().startActivity(intent);
                                }
                            }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show());
                    queue.add(customerPost);
                    queue.start();
                }
            }
        });
    }
}
