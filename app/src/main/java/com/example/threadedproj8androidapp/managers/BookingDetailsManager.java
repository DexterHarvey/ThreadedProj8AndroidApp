package com.example.threadedproj8androidapp.managers;

import androidx.annotation.Nullable;

import com.example.threadedproj8androidapp.model.BookingDetailsEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class BookingDetailsManager {
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
    public static JSONObject buildJSONFromBookingDetails(BookingDetailsEntity bd) {
        JSONObject json = new JSONObject();
        try {
            json.put("itineraryNo", bd.getItineraryNo());
            json.put("tripStart", bd.getTripStart());
            json.put("tripEnd", bd.getTripEnd());
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
