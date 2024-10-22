package com.nikhilt.ridematch.services;

import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Rider;
import com.nikhilt.ridematch.exceptions.ride.RideExistsException;

public interface IRideService {

    void addRide(String rideId, Driver driver, Rider rider) throws RideExistsException;
}
