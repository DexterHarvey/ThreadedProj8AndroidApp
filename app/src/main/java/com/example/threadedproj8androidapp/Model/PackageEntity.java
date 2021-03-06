package com.example.threadedproj8androidapp.model;

import android.content.Context;
import android.text.format.DateFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Entity classes made by Dexter.
 */


public class PackageEntity implements Serializable {
    private int packageId;
    private String pkgName;
    private Timestamp pkgStartDate;
    private Timestamp pkgEndDate;
    private String pkgDesc;
    private Double pkgBasePrice;
    private Double pkgAgencyCommission;
    private String regionId;

    public PackageEntity(int packageId, String pkgName, Timestamp pkgStartDate, Timestamp pkgEndDate, String pkgDesc, Double pkgBasePrice, Double pkgAgencyCommission, String regionId) {
        this.packageId = packageId;
        this.pkgName = pkgName;
        this.pkgStartDate = pkgStartDate;
        this.pkgEndDate = pkgEndDate;
        this.pkgDesc = pkgDesc;
        this.pkgBasePrice = pkgBasePrice;
        this.pkgAgencyCommission = pkgAgencyCommission;
        this.regionId = regionId;
    }

    public PackageEntity() {

    }

    @Override
    public String toString() {
        return "PackageEntity{" +
                "pkgName='" + pkgName + '\'' +
                ", pkgDesc='" + pkgDesc + '\'' +
                '}';
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Timestamp getPkgStartDate() {
        return pkgStartDate;
    }

    public void setPkgStartDate(Timestamp pkgStartDate) {
        this.pkgStartDate = pkgStartDate;
    }

    public Timestamp getPkgEndDate() {
        return pkgEndDate;
    }

    public void setPkgEndDate(Timestamp pkgEndDate) {
        this.pkgEndDate = pkgEndDate;
    }

    public String getPkgDesc() {
        return pkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc = pkgDesc;
    }

    public double getPkgBasePrice() {
        return pkgBasePrice;
    }

    public void setPkgBasePrice(Double pkgBasePrice) {
        this.pkgBasePrice = pkgBasePrice;
    }

    public double getPkgAgencyCommission() { return pkgAgencyCommission; }

    public void setPkgAgencyCommission(Double pkgAgencyCommission) {
        this.pkgAgencyCommission = pkgAgencyCommission;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}
