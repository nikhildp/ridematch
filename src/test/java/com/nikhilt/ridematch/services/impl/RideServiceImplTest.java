package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.entities.Ride;
import com.nikhilt.ridematch.entities.Rider;
import com.nikhilt.ridematch.exceptions.ride.IllegalRideStateException;
import com.nikhilt.ridematch.exceptions.ride.RideDoesNotExistException;
import com.nikhilt.ridematch.exceptions.ride.RideException;
import com.nikhilt.ridematch.exceptions.ride.RideExistsException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class RideServiceImplTest {


    // Successfully add a new ride with unique rideId
    @Test
    public void test_add_ride_success() throws RideExistsException {
        RideServiceImpl rideService = new RideServiceImpl();
        Driver driver = new Driver("driver1", new Location(0, 0));
        Rider rider = new Rider("rider1", new Location(0, 0));
        String rideId = "ride1";

        rideService.addRide(rideId, driver, rider);

        Ride ride = rideService.getRide(rideId);
        assertNotNull(ride);
        assertEquals(rideId, ride.getRideId());
    }

    // Stop an existing ride and update its state and details
    @Test
    public void test_stop_ride_success() throws RideException {
        RideServiceImpl rideService = new RideServiceImpl();
        Driver driver = new Driver("driver1", new Location(0, 0));
        Rider rider = new Rider("rider1", new Location(0, 0));
        String rideId = "ride1";
        Location destination = new Location(10, 10);

        rideService.addRide(rideId, driver, rider);
        rideService.stopRide(rideId, destination, 30);

        Ride ride = rideService.getRide(rideId);
        assertTrue(ride.isStopped());
        assertEquals(destination, ride.getDestination());
        assertEquals(30, (int) ride.getTimeTaken());
    }

    // Retrieve an existing ride using its rideId
    @Test
    public void test_get_ride_success() throws RideExistsException {
        RideServiceImpl rideService = new RideServiceImpl();
        Driver driver = new Driver("driver1", new Location(0, 0));
        Rider rider = new Rider("rider1", new Location(0, 0));
        String rideId = "ride1";

        rideService.addRide(rideId, driver, rider);

        Ride ride = rideService.getRide(rideId);
        assertNotNull(ride);
        assertEquals(rideId, ride.getRideId());
    }

    // Attempt to add a ride with an existing rideId throws RideExistsException
    @Test(expected = RideExistsException.class)
    public void test_add_ride_existing_id() throws RideExistsException {
        RideServiceImpl rideService = new RideServiceImpl();
        Driver driver = new Driver("driver1", new Location(0, 0));
        Rider rider = new Rider("rider1", new Location(0, 0));

        String rideId = "ride1";

        rideService.addRide(rideId, driver, rider);

        rideService.addRide(rideId, driver, rider);
    }

    // Attempt to stop a ride that does not exist throws RideDoesNotExistException
    @Test(expected = RideDoesNotExistException.class)
    public void test_stop_nonexistent_ride() throws RideException {
        RideServiceImpl rideService = new RideServiceImpl();
        Location location = new Location(10, 10);

        rideService.stopRide("nonexistent", location, 30);
    }

    // Attempt to stop a ride that is not in the STARTED state throws IllegalRideStateException
    @Test(expected = IllegalRideStateException.class)
    public void test_stop_ride_illegal_state() throws RideException {
        RideServiceImpl rideService = new RideServiceImpl();
        Driver driver = new Driver("driver1", new Location(0, 0));
        Rider rider = new Rider("rider1", new Location(0, 0));
        String rideId = "ride1";
        Location destination = new Location(10, 10);

        rideService.addRide(rideId, driver, rider);
        rideService.stopRide(rideId, destination, 30);

        rideService.stopRide(rideId, destination, 30);
    }

    // Verify that the rideRepository is initialized correctly in the constructor
    @Test
    public void test_ride_repository_initialization() {
        RideServiceImpl rideService = new RideServiceImpl();

        assertNotNull(rideService.rideRepository);
    }

    // Ensure that the ride's distance is calculated correctly when stopping a ride
    @Test
    public void test_ride_distance_calculation() throws RideException {
        RideServiceImpl rideService = new RideServiceImpl();
        Driver driver = new Driver("driver1", new Location(0, 0));
        Rider rider = new Rider("rider1", new Location(0, 0));

        String rideId = "ride1";
        Location destination = new Location(10, 10);

        rider.setLocation(new Location(0, 0));

        rideService.addRide(rideId, driver, rider);
        rideService.stopRide(rideId, destination, 30);

        Ride ride = rideService.getRide(rideId);
        assertEquals(14.14f, ride.getRideDistance(), 0.01f); // Assuming Euclidean distance calculation
    }

    // Validate that the ride's state transitions from STARTED to STOPPED correctly
    @Test
    public void test_ride_state_transition() throws RideException {
        RideServiceImpl rideService = new RideServiceImpl();
        Driver driver = new Driver("driver1", new Location(0, 0));
        Rider rider = new Rider("rider1", new Location(0, 0));

        String rideId = "ride1";
        Location destination = new Location(10, 10);

        rideService.addRide(rideId, driver, rider);

        assertTrue(rideService.getRide(rideId).isInProgress());

        rideService.stopRide(rideId, destination, 30);

        assertTrue(rideService.getRide(rideId).isStopped());
    }

    // Check that the ride's timeTaken is updated correctly when stopping a ride
    @Test
    public void test_time_taken_update() throws RideException {
        RideServiceImpl rideService = new RideServiceImpl();
        Driver driver = new Driver("driver1", new Location(0, 0));
        Rider rider = new Rider("rider1", new Location(0, 0));

        String rideId = "ride1";
        Location destination = new Location(10, 10);

        rideService.addRide(rideId, driver, rider);

        int timeTaken = 45;

        rideService.stopRide(rideId, destination, timeTaken);

        assertEquals(timeTaken, (int) rideService.getRide(rideId).getTimeTaken());
    }

    // Confirm that getRide returns null for non-existent rideId
    @Test
    public void test_get_nonexistent_ride_returns_null() {
        RideServiceImpl rideService = new RideServiceImpl();

        assertNull(rideService.getRide("nonexistent"));
    }
}