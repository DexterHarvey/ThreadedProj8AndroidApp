package com.example.threadedproj8androidapp.util;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.threadedproj8androidapp.R;
import com.example.threadedproj8androidapp.model.BookingDetailsEntity;
import com.example.threadedproj8androidapp.model.BookingEntity;
import com.example.threadedproj8androidapp.model.CustomerEntity;
import com.example.threadedproj8androidapp.model.PackageEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

public class PurchaseActivity extends AppCompatActivity {
    CustomerEntity customer = new CustomerEntity();
    BookingDetailsEntity bookingDetails = new BookingDetailsEntity();
    BookingEntity booking = new BookingEntity();
    PackageEntity packageEntity = new PackageEntity();
    Integer numberOfTravellers;
    double numberOfTravellersDouble;
    Integer randomItineraryNo;
    double randomIntineraryNoDouble;
    Integer maxBookingId = 1331;
    Integer bookingId;
    Integer maxBookingDetailId = 1331;
    TextView lblBDItineraryNoValue;
    TextView lblBDTripStartValue;
    TextView lblBDTripEndValue;
    TextView lblBDDescriptionValue;
    TextView lblBDDestinationValue;
    TextView lblBDTotalPriceValue;
    TextView lblNoOfTravValue;

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
        bookingId = maxBookingId + 1;
        booking.setBookingId(bookingId);
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        booking.setBookingDate(time);
        booking.setBookingNo(bookingNo);
        booking.setTravelerCount(numberOfTravellersDouble);
        booking.setCustomerId(customer.getCustomerId());
        booking.setTripTypeId("B");
        booking.setPackageId(packageEntity.getPackageId());
        bookingDetails.setBookingDetailId(maxBookingDetailId + 1);
        bookingDetails.setItineraryNo(rand.nextDouble() * 1000 + randomIntineraryNoDouble);
        bookingDetails.setTripStart(String.valueOf(packageEntity.getPkgStartDate()));
        bookingDetails.setTripEnd(String.valueOf(packageEntity.getPkgEndDate()));
        bookingDetails.setDescription(packageEntity.getPkgDesc());
        bookingDetails.setDestination(packageEntity.getPkgName());
        bookingDetails.setBasePrice(packageEntity.getPkgBasePrice());
        bookingDetails.setAgencyCommission(packageEntity.getPkgAgencyCommission());
        bookingDetails.setBookingId(bookingId);
        double itinNo = bookingDetails.getItineraryNo();
        int itinNoInt = (int) itinNo;
        lblBDItineraryNoValue.setText(String.valueOf(itinNoInt));
        lblBDTripStartValue.setText(String.valueOf(bookingDetails.getTripStart()));
        lblBDTripEndValue.setText(String.valueOf(bookingDetails.getTripEnd()));
        lblBDDescriptionValue.setText(bookingDetails.getDescription());
        lblBDDestinationValue.setText(bookingDetails.getDestination());
        lblBDTotalPriceValue.setText(String.valueOf((bookingDetails.getBasePrice() + bookingDetails.getAgencyCommission())) + "$");
        lblNoOfTravValue.setText(String.valueOf(numberOfTravellers));
    }
}
