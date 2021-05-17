package com.example.threadedproj8androidapp.managers;

import androidx.annotation.Nullable;

import com.example.threadedproj8androidapp.model.BookingDetailsEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.Math.round;

/**
 * Manager class made by Dexter, with Eric assisting in debugging
 */


public class BookingDetailsManager {
    public static BookingDetailsEntity buildBookingDetails(JSONObject bookingData) throws JSONException{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("MMM dd, yyyy, HH:mm:ss aaa");
        Gson gson = gsonBuilder.create();
        BookingDetailsEntity booking = gson.fromJson(String.valueOf(bookingData), BookingDetailsEntity.class);
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
