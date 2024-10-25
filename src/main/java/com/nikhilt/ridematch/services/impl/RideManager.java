package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.commands.AddDriver;
import com.nikhilt.ridematch.commands.AddRider;
import com.nikhilt.ridematch.commands.Bill;
import com.nikhilt.ridematch.commands.Match;
import com.nikhilt.ridematch.commands.StartRide;
import com.nikhilt.ridematch.commands.StopRide;
import com.nikhilt.ridematch.constants.Message;
import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Ride;
import com.nikhilt.ridematch.entities.Rider;
import com.nikhilt.ridematch.exceptions.match.MatchException;
import com.nikhilt.ridematch.exceptions.ride.IllegalRideStateException;
import com.nikhilt.ridematch.exceptions.ride.RideException;
import com.nikhilt.ridematch.services.IBillingService;
import com.nikhilt.ridematch.services.IDriverService;
import com.nikhilt.ridematch.services.IMatchingService;
import com.nikhilt.ridematch.services.IRideManager;
import com.nikhilt.ridematch.services.IRideService;
import com.nikhilt.ridematch.services.IRiderService;

import java.util.List;
import java.util.stream.Collectors;

import static com.nikhilt.ridematch.constants.Message.DRIVERS_MATCHED;
import static com.nikhilt.ridematch.constants.Message.INVALID_RIDE;
import static com.nikhilt.ridematch.constants.Message.NO_DRIVERS_AVAILABLE;
import static com.nikhilt.ridematch.constants.Message.RIDE_NOT_COMPLETED;
import static com.nikhilt.ridematch.constants.Message.RIDE_STOPPED;

public class RideManager extends IRideManager {

    private static final int DRIVER_LIMIT = 5;
    private static final int RANGE = 5;

    IDriverService driverService;
    IRiderService riderService;
    IRideService rideService;
    IMatchingService matchingService;
    IBillingService billingService;

    public RideManager() {
        this.driverService = new DriverServiceImpl();
        this.riderService = new RiderServiceImpl();
        this.rideService = new RideServiceImpl();
        this.matchingService = new MatchingServiceImpl();
        this.billingService = new BillingServiceImpl();
    }


    @Override
    public String startRide(StartRide startRide) {
        Driver nthDriver;
        try {
            nthDriver = matchingService.getNthMatch(startRide.getRiderId(), startRide.getN());
        } catch (MatchException e) {
            return Message.NO_DRIVERS_AVAILABLE;
        }

        Rider rider = riderService.getRider(startRide.getRiderId());

        try {
            rideService.addRide(startRide.getRideId(), nthDriver, rider);
            nthDriver.setAvailable(false);
        } catch (RideException e) {
            return INVALID_RIDE;
        }

        return Message.RIDE_STARTED + " " + startRide.getRideId();
    }

    @Override
    public String stopRide(StopRide stopRide) {
        try {
            rideService.stopRide(stopRide.getRideId(), stopRide.getLocation(), stopRide.getTimeTaken());
            driverService.updateDriverState(rideService.getRide(stopRide.getRideId()).getDriver().getDriverId(), true);
        } catch (RideException e) {
            return INVALID_RIDE;
        }
        return RIDE_STOPPED + " " + stopRide.getRideId();
    }

    @Override
    public String generateBill(Bill bill) {
        Ride ride = rideService.getRide(bill.getRideId());
        if (ride == null) {
            return INVALID_RIDE;
        }
        try {
            return billingService.generateBill(ride).toString();
        } catch (IllegalRideStateException e) {
            return RIDE_NOT_COMPLETED;
        }
    }

    @Override
    public void addDriver(AddDriver addDriver) {
        driverService.addDriver(addDriver.getDriverId(), addDriver.getLocation());
    }

    @Override
    public void addRider(AddRider addRider) {
        riderService.addRider(addRider.getRiderId(),addRider.getLocation());
    }

    @Override
    public String match(Match match) {
        Rider rider = riderService.getRider(match.getRiderId());
        if (rider == null) {
            throw new NullPointerException("Rider " + match.getRiderId() + " not found");
        }
        List<Driver> nearByDrivers = driverService.getNearByDrivers(rider.getLocation(), RANGE, DRIVER_LIMIT);
        if (nearByDrivers.isEmpty()) {
            return NO_DRIVERS_AVAILABLE;
        }
        matchingService.addMatch(match.getRiderId(), nearByDrivers);
        return DRIVERS_MATCHED + " " + nearByDrivers.stream()
                .map(Driver::getDriverId)
                .collect(Collectors.joining(" "));
    }
}
