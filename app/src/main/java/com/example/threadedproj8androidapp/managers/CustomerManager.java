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
    public static JSONObject buildJSONFromCustomer(CustomerEntity customerEntity) {
        JSONObject json = new JSONObject();
        try {
            json.put("custFirstName", customerEntity.getCustFirstName());
            json.put("custLastName", customerEntity.getCustLastName());
            json.put("custAddress", customerEntity.getCustAddress());
            json.put("custCity", customerEntity.getCustCity());
            json.put("custProv", customerEntity.getCustProv());
            json.put("custPostal", customerEntity.getCustPostal());
            json.put("custCountry", customerEntity.getCustCountry());
            json.put("custHomePhone", customerEntity.getCustHomePhone());
            json.put("custBusPhone", customerEntity.getCustBusPhone());
            json.put("custEmail", customerEntity.getCustEmail());
            json.put("username", customerEntity.getUsername());
            json.put("password", customerEntity.getPassword());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
