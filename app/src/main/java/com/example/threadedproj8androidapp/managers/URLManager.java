package com.example.threadedproj8androidapp.managers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLManager {
    public static String getLoginURL(String inputUsername, String inputPassword) {
        try {
            String encodedUsername = URLEncoder.encode(inputUsername, StandardCharsets.UTF_8.toString());
            String encodedPassword = URLEncoder.encode(inputPassword, StandardCharsets.UTF_8.toString());
            return "http://10.0.2.2:8080/RESTApiForAndroid_war_exploded/api/customers/getlogin/" + encodedUsername + "/" + encodedPassword;
        } catch (UnsupportedEncodingException e) {
            return e.toString();
        }
    }
    public static String getBookingDetailsURL(String custId) {
        try {
            String encodedCustId = URLEncoder.encode(custId, StandardCharsets.UTF_8.toString());
            return "http://10.0.2.2:8080/RESTApiForAndroid_war_exploded/api/bookingdetails/getbookingdetailsbycustid/" + encodedCustId;
        } catch (UnsupportedEncodingException e) {
            return e.toString();
        }
    }
    public static String getCustDetailsPutURL() {
        return "http://10.0.2.2:8080/RESTApiForAndroid_war_exploded/api/customers/update";
    }
    public static String getBookingsPostURL() {
        return "http://10.0.2.2:8080/RESTApiForAndroid_war_exploded/api/bookings/addnew";
    }
    public static String getBookingDetailsPostURL() {
        return "http://10.0.2.2:8080/RESTApiForAndroid_war_exploded/api/bookingdetails/addnew";
    }
    public static String getHighestBookingIdURL() {
        return "http://10.0.2.2:8080/RESTApiForAndroid_war_exploded/api/bookings/highestbookingid";
    }
}
