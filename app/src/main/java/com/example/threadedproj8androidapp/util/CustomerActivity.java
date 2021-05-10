package com.example.threadedproj8androidapp.util;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.threadedproj8androidapp.managers.BookingDetailsManager;
import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.managers.URLManager;
import com.example.threadedproj8androidapp.adapter.BookingDetailsAdapter;
import com.example.threadedproj8androidapp.model.BookingDetailsEntity;
import com.example.threadedproj8androidapp.model.CustomerEntity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity implements BookingDetailsAdapter.OnBookingDetailListener {
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
    RecyclerView rvBookingDetails;
    BookingDetailsAdapter adapter;
    RequestQueue queue;
    ArrayList<BookingDetailsEntity> bookings = new ArrayList<>();
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
    }

    @Override
    public void onBookingDetailClick(int position) {
        BookingDetailsEntity selectedBooking = bookings.get(position);
        Intent intent = new Intent(this, BookingDetailsActivity.class);
        intent.putExtra("booking", selectedBooking);
        startActivity(intent);
    }
}