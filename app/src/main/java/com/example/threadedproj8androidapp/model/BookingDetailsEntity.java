package com.example.threadedproj8androidapp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Entity classes made by Dexter
 */

public class BookingDetailsEntity implements Serializable {
    private int bookingDetailId;
    private Double itineraryNo;
    private Timestamp tripStart;
    private Timestamp tripEnd;
    private String description;
    private String destination;
    private double basePrice;
    private double agencyCommission;
    private Integer bookingId;
    private String regionId;
    private String classId;
    private String feeId;
    private Integer productSupplierId;

    public int getBookingDetailId() {
        return bookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    public Double getItineraryNo() {
        return itineraryNo;
    }

    public void setItineraryNo(Double itineraryNo) {
        this.itineraryNo = itineraryNo;
    }

    public Timestamp getTripStart() {
        return tripStart;
    }

    public void setTripStart(Timestamp tripStart) {
        this.tripStart = tripStart;
    }

    public Timestamp getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(Timestamp tripEnd) {
        this.tripEnd = tripEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getAgencyCommission() {
        return agencyCommission;
    }

    public void setAgencyCommission(double agencyCommission) {
        this.agencyCommission = agencyCommission;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public Integer getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(Integer productSupplierId) {
        this.productSupplierId = productSupplierId;
    }
}
