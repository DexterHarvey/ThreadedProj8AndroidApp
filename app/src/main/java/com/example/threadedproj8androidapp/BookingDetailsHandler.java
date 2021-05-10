package com.example.threadedproj8androidapp;

import org.json.JSONException;
import org.json.JSONObject;

public class BookingDetailsHandler {
    public static BookingDetailsEntity buildBookingDetails(JSONObject bookingData) throws JSONException {
        BookingDetailsEntity booking = new BookingDetailsEntity();
        booking.setBookingDetailId(bookingData.getInt("bookingDetailId"));
        booking.setItineraryNo(bookingData.getDouble("itineraryNo"));
        booking.setTripStart(bookingData.getString("tripStart"));
        booking.setTripEnd(bookingData.getString("tripEnd"));
        booking.setDescription(bookingData.getString("description"));
        booking.setDestination(bookingData.getString("destination"));
        booking.setBasePrice(bookingData.getDouble("basePrice"));
        booking.setAgencyCommission(bookingData.getDouble("agencyCommission"));
        booking.setBookingId(bookingData.getInt("bookingId"));
        booking.setRegionId(bookingData.getString("regionId"));
        booking.setClassId(bookingData.getString("classId"));
        booking.setFeeId(bookingData.getString("feeId"));
        booking.setProductSupplierId(bookingData.getInt("productSupplierId"));
        return booking;
    }
}
