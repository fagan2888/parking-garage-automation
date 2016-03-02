package com.glleung.parkinggarageautomation;

/**
 * Created by deanrexines on 3/2/16.
 */
public class Reservation {
    private int arrivalTime;
    private int departureTime;
    private int garageLevel;
    private String parkingSpot;
    private User user;

    public Reservation() {}

    public Reservation(int arrivalTime,
                       int departureTime,
                       int garageLevel,
                       String parkingSpot,
                       User user) {
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.garageLevel = garageLevel;
        this.parkingSpot = parkingSpot;
        this.user = user;
    }

    public int getArrivalTime() {
        return firstName;
    }
    public int getDepartureTime() {
        return lastName;
    }

    public int getGarageLevel() {
        return email;
    }

    public String getParkingSpot() {
        return phone;
    }

    public User getUser() {
        return user;
    }
}//end Reservation

