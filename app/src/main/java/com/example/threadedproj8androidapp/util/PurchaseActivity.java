package com.example.threadedproj8androidapp.util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.managers.BookingDetailsManager;
import com.example.threadedproj8androidapp.managers.BookingsManager;
import com.example.threadedproj8androidapp.managers.FormatHelper;
import com.example.threadedproj8androidapp.managers.URLManager;
import com.example.threadedproj8androidapp.model.BookingDetailsEntity;
import com.example.threadedproj8androidapp.model.BookingsEntity;
import com.example.threadedproj8androidapp.model.CustomerEntity;
import com.example.threadedproj8androidapp.model.PackageEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;

/**
 * Activity for viewing purchase details, allowing user choices in purchase, and purchase submission.
 * Fix to traveler count bug by Eric, remaining code by Dexter.
 */

public class PurchaseActivity extends AppCompatActivity {

    // Declare class vars
    CustomerEntity customer = new CustomerEntity();
    BookingDetailsEntity bookingDetails = new BookingDetailsEntity();
    BookingsEntity booking = new BookingsEntity();
    PackageEntity packageEntity = new PackageEntity();
    Integer numberOfTravellers, randomItineraryNo;
    double numberOfTravellersDouble, randomIntineraryNoDouble;
    TextView lblBDItineraryNoValue, lblBDTripStartValue, lblBDTripEndValue, lblBDDescriptionValue, lblBDDestinationValue, lblBDTotalPriceValue;
    Spinner ddlClassId, ddlNoOfTravelers;
    Button btnPurchaseConfirm;
    RequestQueue queue;
    Integer[] travellers = {1, 2, 3, 4, 5, 6};
    String[] classKeys = {"BSN", "DBL", "DLX", "ECN", "FST", "INT", "OCNV", "SNG"};
    String[] classes = {"Business", "Double", "Deluxe", "Economy", "First Class", "Interior", "Ocean View", "Single"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        // Set up booking info data
        String bookingNo;
        Intent intent = getIntent();
        Random rand = new Random();
        lblBDItineraryNoValue = findViewById(R.id.lblBDItineraryNoValue);
        lblBDTripStartValue = findViewById(R.id.lblBDTripStartValue);
        lblBDTripEndValue = findViewById(R.id.lblBDTripEndValue);
        lblBDDescriptionValue = findViewById(R.id.lblBDDescriptionValue);
        lblBDDestinationValue = findViewById(R.id.lblBDDestinationValue);
        lblBDTotalPriceValue = findViewById(R.id.lblBDTotalPriceValue);
        ddlClassId = findViewById(R.id.ddlClassId);
        ddlNoOfTravelers = findViewById(R.id.ddlNoOfTravelers);
        btnPurchaseConfirm = findViewById(R.id.btnPurchaseConfirm);

        // Prepare key/value pairs for spinners
        String[] spinnerArray = new String[classes.length];
        HashMap<Integer, String> spinnerMap = new HashMap<Integer, String >();
        for (int i = 0; i < classKeys.length; i++) {
            spinnerMap.put(i, classKeys[i]);
            spinnerArray[i] = classes[i];
        }
        // Add values to spinners
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, travellers);
        ddlNoOfTravelers.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        ddlClassId.setAdapter(adapter1);

        // Generate booking number
        char bookNo1 = (char)(rand.nextInt(26) + 'A');
        char bookNo2 = (char)(rand.nextInt(26) + 'A');
        char bookNo3 = (char)(rand.nextInt(26) + 'A');
        Integer bookNo4 = (rand.nextInt(10));
        Integer bookNo5 = (rand.nextInt(10));
        Integer bookNo6 = (rand.nextInt(10));
        bookingNo = String.valueOf(bookNo1) + String.valueOf(bookNo2) + String.valueOf(bookNo3) + String.valueOf(bookNo4) + String.valueOf(bookNo5) + String.valueOf(bookNo6);

        // Generate random itinerary number
        randomItineraryNo = (rand.nextInt(1000));
        randomIntineraryNoDouble = randomItineraryNo;

        // Grab needed data from intent
        customer = (CustomerEntity) intent.getSerializableExtra("customer");
        packageEntity = (PackageEntity) intent.getSerializableExtra("package");

        // Populate new booking and details
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

        // Populate fields based on booking details
        double itinNo = bookingDetails.getItineraryNo();
        int itinNoInt = (int) itinNo;
        lblBDItineraryNoValue.setText(String.valueOf(itinNoInt));
        lblBDTripStartValue.setText(FormatHelper.getNiceDateFormat(bookingDetails.getTripStart()));
        lblBDTripEndValue.setText(FormatHelper.getNiceDateFormat(bookingDetails.getTripEnd()));
        lblBDDescriptionValue.setText(bookingDetails.getDescription());
        lblBDDestinationValue.setText(bookingDetails.getDestination());
        lblBDTotalPriceValue.setText(FormatHelper.getNiceMoneyFormat(bookingDetails.getBasePrice()));

        // On purchase confirm click, send http request to add new booking
        btnPurchaseConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classId = spinnerMap.get(ddlClassId.getSelectedItemPosition());
                bookingDetails.setClassId((String) spinnerMap.get(ddlClassId.getSelectedItemPosition()));
                numberOfTravellers = (Integer) ddlNoOfTravelers.getSelectedItem();
                numberOfTravellersDouble = numberOfTravellers;
                booking.setTravelerCount(numberOfTravellersDouble);
                Log.e("TAG", "onClick: " + numberOfTravellersDouble );
                ArrayList<BookingsEntity> bookingEntities = new ArrayList<>();
                JSONObject bookingJSON = BookingsManager.buildJSONFromBooking(booking);
                queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest bookingPost = new JsonObjectRequest(Request.Method.POST, URLManager.getBookingsPostURL(), bookingJSON,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                // add response to the list of bookings
                                BookingsEntity returnedBooking = BookingsManager.buildBooking(response);
                                bookingEntities.add(returnedBooking);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // Once booked, send a followup request to grab the new highest bookingID
                                Executors.newSingleThreadExecutor().execute(new PurchaseActivity.GETHighestBookingId());
                            }
                        }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show());
                queue.add(bookingPost);
                queue.start();


            }
        });
    }

    // Grabs the highest booking id (ie, the one just created) after booking creation to use in the new booking detail creation, then goes on to post the bookingdetails
    public class GETHighestBookingId implements Runnable {
        @Override
        public void run() {
            JsonObjectRequest highestBookingId = new JsonObjectRequest(Request.Method.GET, URLManager.getHighestBookingIdURL(), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Set the booking id into the bookingDetail data object
                                bookingDetails.setBookingId(response.getInt("highestBookingId"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                            // Go on to create that booking detail
                            Executors.newSingleThreadExecutor().execute(new PurchaseActivity.POSTBookingDetails());
                        }
                    }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show());
            queue.add(highestBookingId);
        }
    }

    // Makes http request to add new booking details after new booking creation
    public class POSTBookingDetails implements Runnable {

        @Override
        public void run() {
            JSONObject bookingDetailsJSON = BookingDetailsManager.buildJSONFromBookingDetails(bookingDetails);
            JsonObjectRequest bookingDetailPost = new JsonObjectRequest(Request.Method.POST, URLManager.getBookingDetailsPostURL(), bookingDetailsJSON,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // After successful response, go to customer activity with customer data in intent
                                BookingDetailsEntity test = BookingDetailsManager.buildBookingDetails(response);
                                Toast.makeText(getApplicationContext(), "Successfully purchased " + test.getDestination(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
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
}
