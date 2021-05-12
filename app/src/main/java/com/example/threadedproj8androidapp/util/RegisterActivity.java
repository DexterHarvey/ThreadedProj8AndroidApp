package com.example.threadedproj8androidapp.util;

import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.managers.CustomerManager;

import org.json.JSONObject;

import java.util.ArrayList;

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
        ArrayList<String> strings = new ArrayList<>();
        txtCustHomePhoneReg.getText();
        btnRegisterCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strings.add(txtCustFNameReg.getText().toString());
                strings.add(txtCustLNameReg.getText().toString());
                strings.add(txtCustAddressReg.getText().toString());
                strings.add(txtCustCityReg.getText().toString());
                strings.add(txtCustProvinceReg.getText().toString());
                strings.add(txtCustPostalCodeReg.getText().toString());
                strings.add(txtCustCountryReg.getText().toString());
                strings.add(PhoneNumberUtils.formatNumber(txtCustHomePhoneReg.getText().toString()));
                strings.add(PhoneNumberUtils.formatNumber(txtCustBusPhoneReg.getText().toString()));
                strings.add(txtCustEmailReg.getText().toString());
                strings.add(txtUsernameReg.getText().toString());
                strings.add(txtPasswordReg.getText().toString());
                JSONObject customer = CustomerManager.buildJSONFromCustomer(null, strings);
                Toast.makeText(getApplicationContext(), customer.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
