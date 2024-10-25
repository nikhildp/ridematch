package com.nikhilt.ridematch.services;

import com.nikhilt.ridematch.dtos.RideSummary;
import com.nikhilt.ridematch.entities.Ride;
import com.nikhilt.ridematch.exceptions.ride.IllegalRideStateException;

public interface IBillingService {
    RideSummary generateBill(Ride ride) throws IllegalRideStateException;
}
