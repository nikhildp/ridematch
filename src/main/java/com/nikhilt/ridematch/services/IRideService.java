package com.nikhilt.ridematch.services;

import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.entities.Ride;
import com.nikhilt.ridematch.entities.Rider;
import com.nikhilt.ridematch.exceptions.ride.RideException;

public interface IRideService {

    void addRide(String rideId, Driver driver, Rider rider) throws RideException;

    void stopRide(String rideId, Location location, int timeTaken) throws RideException;

    Ride getRide(String rideId);
}
