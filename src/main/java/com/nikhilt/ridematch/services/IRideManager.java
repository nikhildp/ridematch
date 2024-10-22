package com.nikhilt.ridematch.services;

import com.nikhilt.ridematch.dtos.RideSummary;
import com.nikhilt.ridematch.entities.Location;

public abstract class IRideManager {

    public abstract String startRide(String rideId, int n, String riderId);

    public abstract String stopRide(String rideId, Location destination, int timeTakenInMin);

    public abstract RideSummary generateBill(String rideId);

    public abstract void match(String riderId);
}
