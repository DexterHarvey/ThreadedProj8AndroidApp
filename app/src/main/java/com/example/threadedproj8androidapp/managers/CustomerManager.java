package com.example.threadedproj8androidapp.managers;

import com.example.threadedproj8androidapp.model.CustomerEntity;

import org.json.JSONException;
import org.json.JSONObject;


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
}
