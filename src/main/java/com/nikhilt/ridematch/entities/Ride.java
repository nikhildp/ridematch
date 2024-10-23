package com.nikhilt.ridematch.entities;

import com.nikhilt.ridematch.constants.RideState;

public class Ride {
    private final String rideId;
    private final Rider rider;
    private final Driver driver;
    private final Location source;
    private Location destination;
    private Integer timeTaken;
    private RideState state;
    private Float fare;

    public Ride(String rideId, Driver driver, Rider rider) {
        this.rideId = rideId;
        this.rider = rider;
        this.driver = driver;
        this.source = rider.getLocation();
        this.state = RideState.STARTED;
    }

    public Integer getTimeTaken() {
        return timeTaken;
    }

    public Float getFare() {
        return fare;
    }

    public void setFare(Float fare) {
        this.fare = fare;
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }


    public boolean stopRide(Location destination, Integer timeTaken) {
        if (state != RideState.STARTED) {
            return false;
        }

        this.destination = destination;
        this.timeTaken = timeTaken;
        state = RideState.STOPPED;
        return true;
    }

    public String getRideId() {
        return rideId;
    }

    public Driver getDriver() {
        return driver;
    }
}
