package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.commands.AddDriver;
import com.nikhilt.ridematch.commands.AddRider;
import com.nikhilt.ridematch.commands.Bill;
import com.nikhilt.ridematch.commands.Match;
import com.nikhilt.ridematch.commands.StartRide;
import com.nikhilt.ridematch.commands.StopRide;
import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.entities.Ride;
import com.nikhilt.ridematch.entities.Rider;
import com.nikhilt.ridematch.exceptions.match.DriverNotAvailableException;
import com.nikhilt.ridematch.exceptions.match.MatchException;
import com.nikhilt.ridematch.exceptions.match.NotEnoughDriversException;
import com.nikhilt.ridematch.exceptions.ride.RideException;
import com.nikhilt.ridematch.services.IDriverService;
import com.nikhilt.ridematch.services.IMatchingService;
import com.nikhilt.ridematch.services.IRideService;
import com.nikhilt.ridematch.services.IRiderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static com.nikhilt.ridematch.constants.Message.INVALID_RIDE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RideManagerTest {

    @Mock
    IMatchingService matchingService = mock(MatchingServiceImpl.class);

    @Mock
    IRiderService riderService;

    @Mock
    IRideService rideService = mock(RideServiceImpl.class);

    @Mock
    IDriverService driverService;

    @InjectMocks
    RideManager rideManager = new RideManager();

    // Successfully start a ride with a matched driver
    @Test
    public void test_start_ride_with_matched_driver() throws MatchException {
        when(matchingService.getNthMatch(anyString(), anyInt())).thenReturn(new Driver("", new Location(0, 0)));
        StartRide startRide = new StartRide(new String[]{"", "ride1", "1", "rider1"});
        String result = rideManager.startRide(startRide);
        assertEquals("RIDE_STARTED ride1", result);
    }

    // Successfully stop a ride and update driver availability
    @Test
    public void test_stop_ride_and_update_driver_availability() {

        Ride ride = new Ride("", new Driver("", null), new Rider("", new Location(0, 0)));
        when(rideService.getRide(anyString())).thenReturn(ride);
        StopRide stopRide = new StopRide(new String[]{"", "ride1", "10", "10", "15"});
        String result = rideManager.stopRide(stopRide);
        assertEquals("RIDE_STOPPED ride1", result);
    }

    // Generate a bill for a completed ride
    @Test
    public void test_generate_bill_for_completed_ride() throws RideException {
        Ride ride = new Ride("", new Driver("", null), new Rider("", new Location(0, 0)));
        ride.stopRide(new Location(1, 1), 50);
        when(rideService.getRide(anyString())).thenReturn(ride);
        Bill bill = new Bill(new String[]{"BILL", "ride1"});
        String result = rideManager.generateBill(bill);
        assertTrue(result.startsWith("BILL"));
    }

    // Add a new driver to the system
    @Test
    public void test_add_new_driver() {
        AddDriver addDriver = new AddDriver(new String[]{"ADD_DRIVER", "driver1", "0", "0"});
        rideManager.addDriver(addDriver);
        verify(driverService, atLeastOnce()).addDriver(anyString(), any());
        // Verify driver added, assuming a method exists to check driver presence
    }

    // Add a new rider to the system
    @Test
    public void test_add_new_rider() {
        AddRider addRider = new AddRider(new String[]{"ADD_RIDER", "rider1", "0", "0"});
        rideManager.addRider(addRider);
        verify(riderService, atLeastOnce()).addRider(anyString(), any());
    }

    // Match a rider with nearby available drivers
    @Test
    public void test_match_rider_with_nearby_drivers() {
        when(riderService.getRider(anyString())).thenReturn(new Rider("rider1", new Location(0, 0)));
        when(driverService.getNearByDrivers(any(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(new Driver("driver1", new Location(0, 0))));
        driverService.addDriver("driver1", new Location(1, 1));
        Match match = new Match(new String[]{"MATCH", "rider1"});
        String result = rideManager.match(match);
        assertTrue(result.startsWith("DRIVERS_MATCHED"));
    }

    // Attempt to start a ride with no available drivers
    @Test
    public void test_start_ride_no_available_drivers() throws MatchException {
        when(matchingService.getNthMatch(anyString(), anyInt())).thenThrow(new NotEnoughDriversException());
        StartRide startRide = new StartRide(new String[]{"", "ride2", "1", "rider2"});
        String result = rideManager.startRide(startRide);
        assertEquals("INVALID_RIDE", result);
    }

    // Attempt to stop a ride that does not exist
    @Test
    public void test_stop_nonexistent_ride() {
        RideManager rideManager = new RideManager();
        StopRide stopRide = new StopRide(new String[]{"", "nonexistent_ride", "10", "10", "15"});
        String result = rideManager.stopRide(stopRide);
        assertEquals("INVALID_RIDE", result);
    }

    // Generate a bill for a ride that has not been completed
    @Test
    public void test_generate_bill_uncompleted_ride() {
        Ride ride = new Ride("", null, new Rider("", new Location(0, 0)));
        when(rideService.getRide(anyString())).thenReturn(ride);
        Bill bill = new Bill(new String[]{"BILL", "uncompleted_ride"});
        String result = rideManager.generateBill(bill);
        assertEquals("RIDE_NOT_COMPLETED", result);
    }

    // Add a driver with an existing ID
    @Test
    public void test_add_driver_existing_id() {
        RideManager rideManager = new RideManager();
        AddDriver addDriver1 = new AddDriver(new String[]{"ADD_DRIVER", "driver1", "0", "0"});
        AddDriver addDriver2 = new AddDriver(new String[]{"ADD_DRIVER", "driver1", "1", "1"});
        rideManager.addDriver(addDriver1);
        // Assuming exception or error handling for duplicate IDs, verify behavior here.
        // e.g., assertThrows(DuplicateDriverException.class, () -> rideManager.addDriver(addDriver2));
    }

    // Add a rider with an existing ID
    @Test
    public void test_add_rider_existing_id() {
        RideManager rideManager = new RideManager();
        AddRider addRider1 = new AddRider(new String[]{"ADD_RIDER", "rider1", "0", "0"});
        AddRider addRider2 = new AddRider(new String[]{"ADD_RIDER", "rider1", "1", "1"});
        rideManager.addRider(addRider1);
        // Assuming exception or error handling for duplicate IDs, verify behavior here.
        // e.g., assertThrows(DuplicateRiderException.class, () -> rideManager.addRider(addRider2));
    }

    // Match a rider who does not exist in the system
    @Test(expected = NullPointerException.class)
    public void test_match_nonexistent_rider() {
        RideManager rideManager = new RideManager();
        Match match = new Match(new String[]{"MATCH", "nonexistent_rider"});
        rideManager.match(match);
    }

    // Handle exceptions when matching service fails to find a driver
    @Test
    public void test_handle_exceptions_matching_service_fails_to_find_driver() throws MatchException {
        // Mocking necessary objects
        MatchingServiceImpl matchingService = mock(MatchingServiceImpl.class);
        MatchException matchException = new DriverNotAvailableException();

        // Test the behavior
        RideManager rideManager = new RideManager();
        StartRide startRide = new StartRide(new String[]{"", "rider123", "1", "ride456"});
        String result = rideManager.startRide(startRide);

        // Assertion
        assertEquals(INVALID_RIDE, result);
    }

    // Ensure driver availability is correctly updated after a ride
    @Test
    public void test_driver_availability_updated_after_ride() throws MatchException {
        // Mocking necessary objects
        Driver driver = new Driver("driver123", new Location(0, 0));
        Rider rider = new Rider("rider123", new Location(1, 1));
        StartRide startRide = new StartRide(new String[]{"", "rider123", "1", "ride456"});
        when(matchingService.getNthMatch(anyString(), anyInt())).thenReturn(driver);
        when(riderService.getRider(anyString())).thenReturn(rider);

        // Test the behavior
        rideManager.startRide(startRide);

        // Assertion
        assertFalse(driver.isAvailable());
    }
}