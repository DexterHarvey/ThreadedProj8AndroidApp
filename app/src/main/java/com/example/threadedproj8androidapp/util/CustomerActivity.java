package com.example.threadedproj8androidapp.util;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.threadedproj8androidapp.managers.BookingDetailsManager;
import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.managers.CustomerManager;
import com.example.threadedproj8androidapp.managers.URLManager;
import com.example.threadedproj8androidapp.adapter.BookingDetailsAdapter;
import com.example.threadedproj8androidapp.model.BookingDetailsEntity;
import com.example.threadedproj8androidapp.model.CustomerEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Activity to view and update customer details, view customer booking details, and select those details to view more.
 * Addition of GET booking call/ passing traveler count for proper booking price by Eric. All other code by Dexter.
 */

public class CustomerActivity extends AppCompatActivity implements BookingDetailsAdapter.OnBookingDetailListener {

    // Set up class variables
    EditText txtCustFName, txtCustLName, txtCustAddress, txtCustCity, txtCustProvince,
             txtCustPostalCode, txtCustCountry, txtCustHomePhone, txtCustBusPhone, txtCustEmail;
    TextView lblUsernameValue, lblPasswordValue;
    Button btnEditCustomer, btnLogout;
    RecyclerView rvBookingDetails;
    BookingDetailsAdapter adapter;
    RequestQueue queue;
    ArrayList<BookingDetailsEntity> bookings = new ArrayList<>();
    CustomerEntity customer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get customer data from intent and populate fields
        setContentView(R.layout.activity_customer);
        Intent intent = getIntent();
        customer = (CustomerEntity) intent.getSerializableExtra("customer");
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
        btnEditCustomer = findViewById(R.id.btnEditCustomer);
        btnLogout = findViewById(R.id.btnLogout);
        rvBookingDetails = findViewById(R.id.rvBookingDetails);
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
        adapter = new BookingDetailsAdapter(bookings, this);
        lblUsernameValue.setText(customer.getUsername());
        lblPasswordValue.setText(customer.getPassword());
        rvBookingDetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvBookingDetails.setAdapter(adapter);

        // Make http request to get bookingdetails
        queue = Volley.newRequestQueue(this);
        JsonArrayRequest detailsRequest = new JsonArrayRequest(Request.Method.GET, URLManager.getBookingDetailsURL(String.valueOf(customer.getCustomerId())), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i=0; i < response.length(); i++) {
                                BookingDetailsEntity bd = BookingDetailsManager.buildBookingDetails(response.getJSONObject(i));
                                bookings.add(bd);
                                adapter.updateData(bookings);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show());
        queue.add(detailsRequest);
        queue.start();

        // On Edit button click, create new customer object using fields, then send request to update customer
        btnEditCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> ints = new ArrayList<Integer>();
                ArrayList<String> strings = new ArrayList<String>();
                Integer custId = customer.getCustomerId();
                ints.add(custId);
                customer.setCustFirstName(txtCustFName.getText().toString());
                String inputFirstName = txtCustFName.getText().toString();
                strings.add(inputFirstName);
                customer.setCustLastName(txtCustLName.getText().toString());;
                customer.setCustAddress(txtCustAddress.getText().toString());;
                customer.setCustCity(txtCustCity.getText().toString());
                customer.setCustProv(txtCustProvince.getText().toString());
                customer.setCustPostal(txtCustPostalCode.getText().toString());
                customer.setCustCountry(txtCustCountry.getText().toString());
                customer.setCustHomePhone(txtCustHomePhone.getText().toString());
                customer.setCustBusPhone(txtCustBusPhone.getText().toString());
                customer.setCustEmail(txtCustEmail.getText().toString());
                customer.setUsername(lblUsernameValue.getText().toString());
                customer.setPassword(lblPasswordValue.getText().toString());

                JsonObjectRequest custRequest = new JsonObjectRequest(Request.Method.PUT, URLManager.getCustDetailsPutURL(), CustomerManager.buildJSONFromCustomer(customer),
                        response -> {
                            Log.d("PUT Response", response.toString());
                            Toast.makeText(getApplicationContext(), "Details updated successfully!", Toast.LENGTH_SHORT).show();
                        }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show());
                queue.start();
                queue.add(custRequest);
            }
        });

        // On logout click, go to login activity
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent1.putExtra("isLogout", true);
                getApplicationContext().startActivity(intent1);
            }
        });
    }

    // On click of recyclerview item, start up booking detail popup activity
    // Need to get and pass along number of travelers also - Eric
    @Override
    public void onBookingDetailClick(int position) {
        BookingDetailsEntity selectedBookingDetail = bookings.get(position);
        Double travelerCount;
        Log.e("TAG", "onBookingDetailClick: " + selectedBookingDetail.getBookingId());
        JsonObjectRequest bookingRequest = new JsonObjectRequest(Request.Method.GET,
                "http://10.0.2.2:8080/RESTApiForAndroid_war_exploded/api/bookings/get/" + selectedBookingDetail.getBookingId(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double travelerCount = response.getDouble("travelerCount");
                            Intent intent = new Intent(getBaseContext(), BookingDetailsActivity.class);
                            intent.putExtra("booking", selectedBookingDetail);
                            intent.putExtra("travelerCount", travelerCount);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show());
        queue.add(bookingRequest);
        queue.start();


    }
}

