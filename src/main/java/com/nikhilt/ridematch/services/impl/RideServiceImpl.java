package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.entities.Ride;
import com.nikhilt.ridematch.entities.Rider;
import com.nikhilt.ridematch.exceptions.ride.RideDoesNotExistException;
import com.nikhilt.ridematch.exceptions.ride.RideException;
import com.nikhilt.ridematch.exceptions.ride.RideExistsException;
import com.nikhilt.ridematch.repositories.RideRepository;
import com.nikhilt.ridematch.services.IRideService;

public class RideServiceImpl implements IRideService {
    RideRepository rideRepository;

    RideServiceImpl() {
        rideRepository = new RideRepository();
    }

    @Override
    public void addRide(String rideId, Driver driver, Rider rider) throws RideExistsException {
        if (rideRepository.contains(rideId)) {
            throw new RideExistsException();
        }
        rideRepository.addValue(rideId, new Ride(rideId, driver, rider));
    }

    @Override
    public void stopRide(String rideId, Location location, int timeTaken) throws RideException {
        if (!rideRepository.contains(rideId)) {
            throw new RideDoesNotExistException();
        }
        rideRepository.getValue(rideId).stopRide(location, timeTaken);
    }

    public Ride getRide(String rideId) {
        return rideRepository.getValue(rideId);
    }
}
