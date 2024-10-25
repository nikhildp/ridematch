package com.nikhilt.ridematch.entities;

import com.nikhilt.ridematch.constants.RideState;
import com.nikhilt.ridematch.exceptions.ride.IllegalRideStateException;
import com.nikhilt.ridematch.exceptions.ride.RideException;

public class Ride {
    private final String rideId;
    private final Rider rider;
    private final Driver driver;
    private final Location source;
    private Location destination;
    private float distance;
    private Integer timeTaken;
    private RideState state;
    private float fare;

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

    public double getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    public void stopRide(Location destination, Integer timeTaken) throws RideException {
        if (state != RideState.STARTED) {
            throw new IllegalRideStateException();
        }

        this.destination = destination;
        this.distance = destination.getDistance(source);
        this.timeTaken = timeTaken;
        state = RideState.STOPPED;
    }

    public String getRideId() {
        return rideId;
    }

    public Driver getDriver() {
        return driver;
    }

    public float getRideDistance() throws IllegalRideStateException {
        if(state!=RideState.STOPPED){
            throw new IllegalRideStateException();
        }
        return Float.parseFloat(String.format("%.2f",distance));
    }
}
