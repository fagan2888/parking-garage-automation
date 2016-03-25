package com.glleung.parkinggarageautomation;

/**
 * Created by Alex on 3/23/16.
 */
public class Vehicle {
    public String make;
    public String model;
    public String color;
    public String licenseplate;
    public String year;
    public String uid;

    public Vehicle(String make, String model, String color, String year, String licenseplate, String uid){
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
        this.licenseplate = licenseplate;
        this.uid = uid;
    }
}

