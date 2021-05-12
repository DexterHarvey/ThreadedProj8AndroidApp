package com.example.threadedproj8androidapp.util;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.managers.BookingDetailsManager;
import com.example.threadedproj8androidapp.managers.BookingsManager;
import com.example.threadedproj8androidapp.managers.URLManager;
import com.example.threadedproj8androidapp.model.BookingDetailsEntity;
import com.example.threadedproj8androidapp.model.BookingEntity;
import com.example.threadedproj8androidapp.model.CustomerEntity;
import com.example.threadedproj8androidapp.model.PackageEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;

public class PurchaseActivity extends AppCompatActivity {
    CustomerEntity customer = new CustomerEntity();
    BookingDetailsEntity bookingDetails = new BookingDetailsEntity();
    BookingEntity booking = new BookingEntity();
    PackageEntity packageEntity = new PackageEntity();
    Integer numberOfTravellers;
    double numberOfTravellersDouble;
    Integer randomItineraryNo;
    double randomIntineraryNoDouble;
    TextView lblBDItineraryNoValue;
    TextView lblBDTripStartValue;
    TextView lblBDTripEndValue;
    TextView lblBDDescriptionValue;
    TextView lblBDDestinationValue;
    TextView lblBDTotalPriceValue;
    TextView lblNoOfTravValue;
    Spinner ddlClassId;
    Button btnPurchaseConfirm;
    RequestQueue queue;
    String[] classes = {"BSN", "DBL", "DLX", "ECN", "FST", "INT", "OCNV", "SNG"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        Intent intent = getIntent();
        Random rand = new Random();
        String bookingNo;
        lblBDItineraryNoValue = findViewById(R.id.lblBDItineraryNoValue);
        lblBDTripStartValue = findViewById(R.id.lblBDTripStartValue);
        lblBDTripEndValue = findViewById(R.id.lblBDTripEndValue);
        lblBDDescriptionValue = findViewById(R.id.lblBDDescriptionValue);
        lblBDDestinationValue = findViewById(R.id.lblBDDestinationValue);
        lblBDTotalPriceValue = findViewById(R.id.lblBDTotalPriceValue);
        lblNoOfTravValue = findViewById(R.id.lblNoOfTravValue);
        ddlClassId = findViewById(R.id.ddlClassId);
        btnPurchaseConfirm = findViewById(R.id.btnPurchaseConfirm);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, classes);
        ddlClassId.setAdapter(adapter);
        char bookNo1 = (char)(rand.nextInt(26) + 'A');
        char bookNo2 = (char)(rand.nextInt(26) + 'A');
        char bookNo3 = (char)(rand.nextInt(26) + 'A');
        Integer bookNo4 = (rand.nextInt(10));
        Integer bookNo5 = (rand.nextInt(10));
        Integer bookNo6 = (rand.nextInt(10));
        bookingNo = String.valueOf(bookNo1) + String.valueOf(bookNo2) + String.valueOf(bookNo3) + String.valueOf(bookNo4) + String.valueOf(bookNo5) + String.valueOf(bookNo6);
        randomItineraryNo = (rand.nextInt(1000));
        randomIntineraryNoDouble = randomItineraryNo;
        customer = (CustomerEntity) intent.getSerializableExtra("customer");
        packageEntity = (PackageEntity) intent.getSerializableExtra("package");
        numberOfTravellers = intent.getIntExtra("numberOfTravellers", 0);
        numberOfTravellersDouble = numberOfTravellers;
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        booking.setBookingDate(time);
        booking.setBookingNo(bookingNo);
        booking.setTravelerCount(numberOfTravellersDouble);
        booking.setCustomerId(customer.getCustomerId());
        booking.setTripTypeId("B");
        booking.setPackageId(packageEntity.getPackageId());
        bookingDetails.setItineraryNo(rand.nextDouble() * 1000 + randomIntineraryNoDouble);
        bookingDetails.setTripStart(packageEntity.getPkgStartDate());
        bookingDetails.setTripEnd(packageEntity.getPkgEndDate());
        bookingDetails.setDescription(packageEntity.getPkgDesc());
        bookingDetails.setDestination(packageEntity.getPkgName());
        bookingDetails.setBasePrice(packageEntity.getPkgBasePrice());
        bookingDetails.setAgencyCommission(packageEntity.getPkgAgencyCommission());
        bookingDetails.setRegionId(packageEntity.getRegionId());
        bookingDetails.setFeeId("BK");
        bookingDetails.setProductSupplierId(null);
        double itinNo = bookingDetails.getItineraryNo();
        int itinNoInt = (int) itinNo;
        lblBDItineraryNoValue.setText(String.valueOf(itinNoInt));
        lblBDTripStartValue.setText(String.valueOf(bookingDetails.getTripStart()));
        lblBDTripEndValue.setText(String.valueOf(bookingDetails.getTripEnd()));
        lblBDDescriptionValue.setText(bookingDetails.getDescription());
        lblBDDestinationValue.setText(bookingDetails.getDestination());
        lblBDTotalPriceValue.setText(String.valueOf((bookingDetails.getBasePrice() + bookingDetails.getAgencyCommission())) + "$");
        lblNoOfTravValue.setText(String.valueOf(numberOfTravellers));
        JSONObject bookingJSON = BookingsManager.buildJSONFromBooking(booking);
        btnPurchaseConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingDetails.setClassId((String) ddlClassId.getSelectedItem());
                ArrayList<BookingEntity> bookingEntities = new ArrayList<>();
                queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest bookingPost = new JsonObjectRequest(Request.Method.POST, URLManager.getBookingsPostURL(), bookingJSON,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                BookingEntity returnedBooking = BookingsManager.buildBooking(response);
                                bookingEntities.add(returnedBooking);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Executors.newSingleThreadExecutor().execute(new PurchaseActivity.GETHighestBookingId());
                            }
                        }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show());
                queue.add(bookingPost);
                queue.start();


            }
        });
    }
    public class POSTBookingDetails implements Runnable {

        @Override
        public void run() {
            JSONObject bookingDetailsJSON = BookingDetailsManager.buildJSONFromBookingDetails(bookingDetails);
            JsonObjectRequest bookingDetailPost = new JsonObjectRequest(Request.Method.POST, URLManager.getBookingDetailsPostURL(), bookingDetailsJSON,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                BookingDetailsEntity test = BookingDetailsManager.buildBookingDetails(response);
                                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                                intent.putExtra("customer", customer);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show());
            queue.add(bookingDetailPost);
        }
    }
    public class GETHighestBookingId implements Runnable {

        @Override
        public void run() {
            JsonObjectRequest highestBookingId = new JsonObjectRequest(Request.Method.GET, URLManager.getHighestBookingIdURL(), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                bookingDetails.setBookingId(response.getInt("highestBookingId"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                            Executors.newSingleThreadExecutor().execute(new PurchaseActivity.POSTBookingDetails());
                        }
                    }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show());
            queue.add(highestBookingId);
        }
    }
}
