package com.nikhilt.ridematch.services;

import com.nikhilt.ridematch.dtos.RideSummary;

public interface IBillingService {
    RideSummary generateBill(String rideId);
}
