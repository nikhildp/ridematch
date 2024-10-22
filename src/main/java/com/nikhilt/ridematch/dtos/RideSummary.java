package com.nikhilt.ridematch.dtos;

import com.nikhilt.ridematch.entities.Ride;

public class RideSummary {
    private String rideId;
    private String driverId;
    private Float fare;

    RideSummary(Ride ride) {
        this.rideId = ride.getRideId();
        this.driverId = ride.getDriver().getDriverId();
        this.fare = ride.getFare();
    }

    @Override
    public String toString() {
        return rideId + " " + driverId + " " + fare;
    }
}
