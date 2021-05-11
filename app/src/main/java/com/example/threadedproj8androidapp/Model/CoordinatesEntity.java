package com.example.threadedproj8androidapp.model;

public class CoordinatesEntity {

    private int CoordinateId;
    private int PackageId;
    private double Latitude;
    private double Longitude;
    private String Name;


    public int getCoordinateId() {
        return CoordinateId;
    }

    public void setCoordinateId(int coordinateId) {
        CoordinateId = coordinateId;
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

}
