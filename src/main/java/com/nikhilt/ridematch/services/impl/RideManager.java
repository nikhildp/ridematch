package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.constants.Message;
import com.nikhilt.ridematch.dtos.RideSummary;
import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.entities.Rider;
import com.nikhilt.ridematch.exceptions.match.MatchException;
import com.nikhilt.ridematch.exceptions.ride.RideException;
import com.nikhilt.ridematch.repositories.RiderRepository;
import com.nikhilt.ridematch.services.IDriverService;
import com.nikhilt.ridematch.services.IMatchingService;
import com.nikhilt.ridematch.services.IRideManager;
import com.nikhilt.ridematch.services.IRideService;
import com.nikhilt.ridematch.services.IRiderService;

import java.util.List;

public class RideManager extends IRideManager {

    private static final int DRIVER_LIMIT = 5;
    private static final int RANGE = 5;

    RiderRepository riderRepository;

    IDriverService driverService;
    IRiderService riderService;
    IRideService rideService;
    IMatchingService matchingService;

    public RideManager() {
        this.riderRepository = new RiderRepository();
        this.driverService = new DriverServiceImpl();
        this.riderService = new RiderServiceImpl();
        this.rideService = new RideServiceImpl();
        this.matchingService = new MatchingServiceImpl();
    }


    @Override
    public String startRide(String rideId, int n, String riderId) {
        Driver nthDriver;
        try {
            nthDriver = matchingService.getNthMatch(riderId, n);
        } catch (MatchException e) {
            return Message.NO_DRIVERS_AVAILABLE;
        }

        riderService.getRider(riderId);

        try {
            rideService.addRide(rideId, nthDriver, riderService.getRider(riderId));
        } catch (RideException e) {
            return Message.INVALID_RIDE;
        }

        return Message.RIDE_STARTED + " " + rideId;
    }

    @Override
    public String stopRide(String rideId, Location destination, int timeTakenInMin) {
        return "";
    }

    @Override
    public RideSummary generateBill(String rideId) {
        return null;
    }

    @Override
    public void match(String riderId) {
        Rider rider = riderService.getRider(riderId);
        if (rider == null) {
            throw new NullPointerException("Rider " + riderId + " not found");
        }
        List<Driver> nearByDrivers = driverService.getNearByDrivers(rider.getLocation(), RANGE, DRIVER_LIMIT);
        if (nearByDrivers.isEmpty()) {
            System.out.println("NO_DRIVERS_AVAILABLE");
            return;
        }
        matchingService.addMatch(riderId, nearByDrivers);
    }
}
