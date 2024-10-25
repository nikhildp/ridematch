package com.nikhilt.ridematch.dtos;

import com.nikhilt.ridematch.entities.Ride;

public class RideSummary {
    private final String rideId;
    private final String driverId;
    private final double fare;

    public RideSummary(Ride ride) {
        this.rideId = ride.getRideId();
        this.driverId = ride.getDriver().getDriverId();
        this.fare = ride.getFare();
    }

    @Override
    public String toString() {
        return rideId + " " + driverId + " " + String.format("%.2f", fare);
    }
}
