package com.example.threadedproj8androidapp.model;

public class PkgDestinationsEntity {
    private int pkgDestinationId;
    private int PackageId;
    private double Latitude;
    private double Longitude;
    private String Name;
    private String Country;
    private String Description;

    public int getPkgDestinationId() {
        return pkgDestinationId;
    }

    public void setPkgDestinationId(int pkgDestinationId) {
        this.pkgDestinationId = pkgDestinationId;
    }


    public int getPackageId() {
        return PackageId;
    }

    public void setPackageId(int packageId) {
        PackageId = packageId;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


}
