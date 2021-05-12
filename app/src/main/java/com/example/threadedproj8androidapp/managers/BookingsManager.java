package com.example.threadedproj8androidapp.managers;

import com.example.threadedproj8androidapp.model.BookingDetailsEntity;
import com.example.threadedproj8androidapp.model.BookingEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingsManager {
    public static JSONObject buildJSONFromBooking(BookingEntity booking) {
        JSONObject json = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        String bookingDate = sdf.format(booking.getBookingDate());
        try {
            json.put("bookingDate", bookingDate);
            json.put("bookingNo", booking.getBookingNo());
            json.put("travelerCount", booking.getTravelerCount());
            json.put("customerId", booking.getCustomerId());
            json.put("tripTypeId", booking.getTripTypeId());
            json.put("packageId", booking.getPackageId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
    public static BookingEntity buildBooking(JSONObject bookingData) throws JSONException {
        BookingEntity booking = new BookingEntity();
        booking.setBookingId(bookingData.getInt("bookingId"));
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(bookingData.getString("bookingDate"));
            Timestamp timestampDate = new java.sql.Timestamp(parsedDate.getTime());
            booking.setBookingDate(timestampDate);
        } catch (Exception e) {
            //some code
        }
        booking.setBookingNo(bookingData.getString("bookingNo"));
        booking.setTravelerCount(bookingData.getDouble("travelerCount"));
        booking.setCustomerId(bookingData.getInt("customerId"));
        booking.setTripTypeId(bookingData.getString("tripTypeId"));
        booking.setPackageId(bookingData.getInt("packageId"));
        return booking;
    }
}
