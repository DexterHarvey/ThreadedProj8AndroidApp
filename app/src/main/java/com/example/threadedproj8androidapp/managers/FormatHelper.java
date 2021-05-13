package com.example.threadedproj8androidapp.managers;

import android.text.format.DateFormat;

import java.sql.Timestamp;
import java.text.NumberFormat;

public class FormatHelper {

    public static String getNiceDateFormat(Timestamp origDate){
        DateFormat dateFormat =  new DateFormat();
        return dateFormat.format("MMM dd, yyyy", origDate).toString();
    }

    // Thanks to https://stackoverflow.com/questions/2379221/java-currency-number-format
    public static String getNiceMoneyFormat(double yourMoney){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(yourMoney);
    }

    public static int roundToInt(double dbl){
        return (int) Math.round(dbl);
    }

}
