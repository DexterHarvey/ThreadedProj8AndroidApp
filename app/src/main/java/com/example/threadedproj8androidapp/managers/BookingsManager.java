package com.example.threadedproj8androidapp.managers;

import com.example.threadedproj8androidapp.model.BookingDetailsEntity;
import com.example.threadedproj8androidapp.model.BookingEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class BookingsManager {
    public static JSONObject buildJSONFromBooking(BookingEntity booking) {
        JSONObject json = new JSONObject();
        try {
            json.put("bookingDate", booking.getBookingDate());
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
        booking.setBookingDate(Timestamp.valueOf(bookingData.getString("itineraryNo")));
        booking.setBookingNo(bookingData.getString("bookingNo"));
        booking.setTravelerCount(bookingData.getDouble("travelerCount"));
        booking.setCustomerId(bookingData.getInt("customerId"));
        booking.setTripTypeId(bookingData.getString("tripTypeId"));
        booking.setPackageId(bookingData.getInt("packageId"));
        return booking;
    }
}
