package com.example.threadedproj8androidapp.managers;

import androidx.annotation.Nullable;

import com.example.threadedproj8androidapp.model.CustomerEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomerManager {
    public static CustomerEntity buildCustomer(JSONObject custData) throws JSONException {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId(custData.getInt("customerId"));
        customer.setCustFirstName(custData.getString("custFirstName"));
        customer.setCustLastName(custData.getString("custLastName"));
        customer.setCustAddress(custData.getString("custAddress"));
        customer.setCustCity(custData.getString("custCity"));
        customer.setCustProv(custData.getString("custProv"));
        customer.setCustPostal(custData.getString("custPostal"));
        customer.setCustCountry(custData.getString("custCountry"));
        customer.setCustHomePhone(custData.getString("custHomePhone"));
        customer.setCustBusPhone(custData.getString("custBusPhone"));
        customer.setCustEmail(custData.getString("custEmail"));
        customer.setAgentId(custData.getInt("agentId"));
        customer.setUsername(custData.getString("username"));
        customer.setPassword(custData.getString("password"));
        return customer;
    }
    public static JSONObject buildJSONFromCustomer(@Nullable ArrayList<Integer> ints, ArrayList<String> strings) {
        JSONObject json = new JSONObject();
        if (ints != null) {
            try {
                json.put("customerId", ints.get(0));
                json.put("agentId", ints.get(1));
                json.put("custFirstName", strings.get(0));
                json.put("custLastName", strings.get(1));
                json.put("custAddress", strings.get(2));
                json.put("custCity", strings.get(3));
                json.put("custProv", strings.get(4));
                json.put("custPostal", strings.get(5));
                json.put("custCountry", strings.get(6));
                json.put("custHomePhone", strings.get(7));
                json.put("custBusPhone", strings.get(8));
                json.put("custEmail", strings.get(9));
                json.put("username", strings.get(10));
                json.put("password", strings.get(11));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                json.put("custFirstName", strings.get(0));
                json.put("custLastName", strings.get(1));
                json.put("custAddress", strings.get(2));
                json.put("custCity", strings.get(3));
                json.put("custProv", strings.get(4));
                json.put("custPostal", strings.get(5));
                json.put("custCountry", strings.get(6));
                json.put("custHomePhone", strings.get(7));
                json.put("custBusPhone", strings.get(8));
                json.put("custEmail", strings.get(9));
                json.put("username", strings.get(10));
                json.put("password", strings.get(11));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }
}
