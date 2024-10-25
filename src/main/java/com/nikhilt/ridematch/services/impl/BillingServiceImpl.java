package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.dtos.RideSummary;
import com.nikhilt.ridematch.entities.Ride;
import com.nikhilt.ridematch.exceptions.ride.IllegalRideStateException;
import com.nikhilt.ridematch.services.IBillingService;

public class BillingServiceImpl implements IBillingService {

    private static final float BASE_FARE = 50;
    private static final float PER_KM = 6.5F;
    private static final float PER_MIN = 2;
    private static final int ST_PERCENT = 20;

    @Override
    public RideSummary generateBill(Ride ride) throws IllegalRideStateException {
        calculateFare(ride);
        return new RideSummary(ride);
    }

    private void calculateFare(Ride ride) throws IllegalRideStateException {
        float fare = BASE_FARE
                + PER_KM * ride.getRideDistance()
                + PER_MIN * ride.getTimeTaken();
        fare = fare * (100 + ST_PERCENT) / 100;
        ride.setFare(fare);
    }
}
