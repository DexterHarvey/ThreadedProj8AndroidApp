package com.example.threadedproj8androidapp.util;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.managers.FormatHelper;
import com.example.threadedproj8androidapp.model.BookingDetailsEntity;

/**
 * Popup activity displaying a given booking detail's data.
 * Majority of code by Dexter, with formatting and layout improvements by Eric.
 */

public class BookingDetailsActivity extends AppCompatActivity {
    TextView lblItineraryNoInfo;
    TextView lblTripStartInfo;
    TextView lblTripEndInfo;
    TextView lblDescriptionInfo;
    TextView lblDestinationInfo;
    TextView lblTotalCostInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up popup window
        setContentView(R.layout.activity_booking_details);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * 0.7), WindowManager.LayoutParams.WRAP_CONTENT); // adjusted layout to better fit in space - Eric
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Populate fields via intent extra
        Intent intent = getIntent();
        BookingDetailsEntity booking = (BookingDetailsEntity) intent.getSerializableExtra("booking");
        Double travelerCount = intent.getDoubleExtra("travelerCount", 1);
        lblItineraryNoInfo = findViewById(R.id.lblItineraryInfo);
        lblTripStartInfo = findViewById(R.id.lblTripStartInfo);
        lblTripEndInfo = findViewById(R.id.lblTripEndInfo);
        lblDescriptionInfo = findViewById(R.id.lblDescriptionInfo);
        lblDestinationInfo = findViewById(R.id.lblDestinationInfo);
        lblTotalCostInfo = findViewById(R.id.lblTotalCostInfo);

        lblItineraryNoInfo.setText(String.valueOf(
                FormatHelper.roundToInt(booking.getItineraryNo())));
        lblTripStartInfo.setText(FormatHelper.getNiceDateFormat(booking.getTripStart()));
        lblTripEndInfo.setText(FormatHelper.getNiceDateFormat(booking.getTripEnd()));
        lblDescriptionInfo.setText(booking.getDescription());
        lblDestinationInfo.setText(booking.getDestination());
        lblTotalCostInfo.setText(FormatHelper.getNiceMoneyFormat(booking.getBasePrice() * travelerCount));
    }
}
