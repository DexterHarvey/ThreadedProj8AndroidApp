package com.example.threadedproj8androidapp.managers;

import androidx.annotation.Nullable;

import com.example.threadedproj8androidapp.model.CustomerEntity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Manager class made by Dexter, with Eric assisting in debugging
 */


public class CustomerManager {
    public static CustomerEntity buildCustomer(JSONObject custData) throws JSONException {
        CustomerEntity customer = new Gson().fromJson(String.valueOf(custData), CustomerEntity.class);
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
            json.put("agentId", customerEntity.getAgentId());
            json.put("username", customerEntity.getUsername());
            json.put("password", customerEntity.getPassword());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
