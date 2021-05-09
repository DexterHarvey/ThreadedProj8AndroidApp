package com.example.threadedproj8androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class CustomerActivity extends AppCompatActivity {
    EditText txtCustFName;
    EditText txtCustLName;
    EditText txtCustAddress;
    EditText txtCustCity;
    EditText txtCustProvince;
    EditText txtCustPostalCode;
    EditText txtCustCountry;
    EditText txtCustHomePhone;
    EditText txtCustBusPhone;
    EditText txtCustEmail;
    TextView lblUsernameValue;
    TextView lblPasswordValue;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Intent intent = getIntent();
        CustomerEntity customer = (CustomerEntity) intent.getSerializableExtra("customer");
        txtCustFName = findViewById(R.id.txtCustFName);
        txtCustLName = findViewById(R.id.txtCustLName);
        txtCustAddress = findViewById(R.id.txtCustAddress);
        txtCustCity = findViewById(R.id.txtCustCity);
        txtCustProvince = findViewById(R.id.txtCustProvince);
        txtCustPostalCode = findViewById(R.id.txtCustPostalCode);
        txtCustCountry = findViewById(R.id.txtCustCountry);
        txtCustHomePhone= findViewById(R.id.txtCustHomePhone);
        txtCustBusPhone = findViewById(R.id.txtCustBusPhone);
        txtCustEmail = findViewById(R.id.txtCustEmail);
        lblUsernameValue = findViewById(R.id.lblUsernameValue);
        lblPasswordValue = findViewById(R.id.lblPasswordValue);
        txtCustFName.setText(customer.getCustFirstName());
        txtCustLName.setText(customer.getCustLastName());
        txtCustAddress.setText(customer.getCustAddress());
        txtCustCity.setText(customer.getCustCity());
        txtCustProvince.setText(customer.getCustProv());
        txtCustPostalCode.setText(customer.getCustPostal());
        txtCustCountry.setText(customer.getCustCountry());
        txtCustHomePhone.setText(customer.getCustHomePhone());
        txtCustBusPhone.setText(customer.getCustBusPhone());
        txtCustEmail.setText(customer.getCustEmail());
        lblUsernameValue.setText(customer.getUsername());
        lblPasswordValue.setText(customer.getPassword());
    }
}
