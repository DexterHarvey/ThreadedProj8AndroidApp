package com.example.threadedproj8androidapp.managers;

import androidx.annotation.Nullable;

import com.example.threadedproj8androidapp.model.BookingDetailsEntity;

import org.json.JSONException;
import org.json.JSONObject;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.round;

public class BookingDetailsManager {
    public static BookingDetailsEntity buildBookingDetails(JSONObject bookingData) throws JSONException {
        BookingDetailsEntity booking = new BookingDetailsEntity();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedStartDate = dateFormat.parse(bookingData.getString("tripStart"));
            Date parsedEndDate = dateFormat.parse(bookingData.getString("tripEnd"));
            Timestamp timestampStart = new java.sql.Timestamp(parsedStartDate.getTime());
            Timestamp timestampEnd = new java.sql.Timestamp(parsedEndDate.getTime());
            booking.setTripStart(timestampStart);
            booking.setTripEnd(timestampEnd);
        } catch(Exception e) { //this generic but you can control another types of exception
            // look the origin of excption
        }
        booking.setBookingDetailId(bookingData.getInt("bookingDetailId"));
        booking.setItineraryNo(bookingData.getDouble("itineraryNo"));
        booking.setDescription(bookingData.getString("description"));
        booking.setDestination(bookingData.getString("destination"));
        booking.setBasePrice(bookingData.getDouble("basePrice"));
        booking.setAgencyCommission(bookingData.getDouble("agencyCommission"));
        booking.setBookingId(bookingData.getInt("bookingId"));
        booking.setRegionId(bookingData.getString("regionId"));
        booking.setClassId(bookingData.getString("classId"));
        booking.setFeeId(bookingData.getString("feeId"));
        return booking;
    }
    public static JSONObject buildJSONFromBookingDetails(BookingDetailsEntity bd) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        String tripStart = sdf.format(bd.getTripStart());
        String tripEnd = sdf.format(bd.getTripEnd());
        JSONObject json = new JSONObject();
        try {
            json.put("itineraryNo", round(bd.getItineraryNo()));
            json.put("tripStart", tripStart);
            json.put("tripEnd", tripEnd);
            json.put("description", bd.getDescription());
            json.put("destination", bd.getDestination());
            json.put("basePrice", bd.getBasePrice());
            json.put("agencyCommission", bd.getAgencyCommission());
            json.put("bookingId", bd.getBookingId());
            json.put("regionId", bd.getRegionId());
            json.put("classId", bd.getClassId());
            json.put("feeId", bd.getFeeId());
            json.put("productSupplierId", bd.getProductSupplierId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
