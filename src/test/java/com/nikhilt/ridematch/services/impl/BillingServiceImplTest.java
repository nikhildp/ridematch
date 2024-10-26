package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.dtos.RideSummary;
import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.entities.Ride;
import com.nikhilt.ridematch.entities.Rider;
import com.nikhilt.ridematch.exceptions.ride.IllegalRideStateException;
import com.nikhilt.ridematch.exceptions.ride.RideException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BillingServiceImplTest {


    // Generate a bill for a ride that has been stopped
    @Test
    public void test_generate_bill_for_stopped_ride() throws RideException {
        Ride ride = new Ride("ride1", new Driver("driver1", new Location(0, 0)), new Rider("rider1", new Location(0, 0)));
        ride.stopRide(new Location(10, 10), 30);
        BillingServiceImpl billingService = new BillingServiceImpl();
        RideSummary summary = billingService.generateBill(ride);
        assertNotNull(summary);
        assertEquals("ride1", summary.getRideId());
        assertEquals("242.29", String.format("%.2f", summary.getFare()));
    }

    // Calculate fare using base fare, distance, and time
    @Test
    public void test_calculate_fare_components() throws RideException {
        Ride ride = new Ride("ride2", new Driver("driver2", new Location(0, 0)), new Rider("rider2", new Location(0, 0)));
        ride.stopRide(new Location(20, 20), 40);
        BillingServiceImpl billingService = new BillingServiceImpl();
        billingService.generateBill(ride);
        float expectedFare = 50 + (6.5F * ride.getRideDistance()) + (2 * ride.getTimeTaken());
        expectedFare = expectedFare * 1.2F;
        assertEquals(expectedFare, ride.getFare(), 0.01);
    }

    // Apply service tax percentage to the calculated fare
    @Test
    public void test_apply_service_tax() throws RideException {
        Ride ride = new Ride("ride3", new Driver("driver3", new Location(0, 0)), new Rider("rider3", new Location(0, 0)));
        ride.stopRide(new Location(15, 15), 25);
        BillingServiceImpl billingService = new BillingServiceImpl();
        billingService.generateBill(ride);
        float baseFare = 50 + (6.5F * ride.getRideDistance()) + (2 * ride.getTimeTaken());
        float expectedFareWithTax = baseFare * 1.2F;
        assertEquals(expectedFareWithTax, ride.getFare(), 0.01);
    }

    // Attempt to generate a bill for a ride that is not stopped
    @Test(expected = IllegalRideStateException.class)
    public void test_generate_bill_for_non_stopped_ride() throws IllegalRideStateException {
        Ride ride = new Ride("ride4", new Driver("driver4", new Location(0, 0)), new Rider("rider4", new Location(0, 0)));
        BillingServiceImpl billingService = new BillingServiceImpl();
        billingService.generateBill(ride);
    }

    // Handle rides with zero distance or zero time taken
    @Test
    public void test_handle_zero_distance_or_time() throws RideException {
        Ride ride = new Ride("ride5", new Driver("driver5", new Location(0, 0)), new Rider("rider5", new Location(0, 0)));
        ride.stopRide(new Location(0, 0), 0);
        BillingServiceImpl billingService = new BillingServiceImpl();
        billingService.generateBill(ride);
        float expectedFare = 50 * 1.2F;
        assertEquals(expectedFare, ride.getFare(), 0.01);
    }

    // Calculate fare for rides with very high distance or time taken
    @Test
    public void test_calculate_fare_for_high_distance_or_time() throws RideException {
        Ride ride = new Ride("ride6", new Driver("driver6", new Location(0, 0)), new Rider("rider6", new Location(0, 0)));
        ride.stopRide(new Location(1000, 1000), 1000);
        BillingServiceImpl billingService = new BillingServiceImpl();
        billingService.generateBill(ride);
        float expectedFare = 50 + (6.5F * ride.getRideDistance()) + (2 * ride.getTimeTaken());
        expectedFare = expectedFare * 1.2F;
        assertEquals(expectedFare, ride.getFare(), 0.01);
    }
}