package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.exceptions.match.DriverNotAvailableException;
import com.nikhilt.ridematch.exceptions.match.MatchException;
import com.nikhilt.ridematch.exceptions.match.NotEnoughDriversException;
import com.nikhilt.ridematch.exceptions.match.RideNotFoundException;
import com.nikhilt.ridematch.repositories.MatchRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MatchingServiceImplTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    // Successfully add a match with a valid riderId and list of drivers
    @Test
    public void test_add_match_success() {
        MatchingServiceImpl service = new MatchingServiceImpl();
        List<Driver> drivers = Collections.singletonList(new Driver("driver1", new Location(0, 0)));
        service.addMatch("rider1", drivers);
        assertEquals(drivers, service.matchRepository.getValue("rider1"));
    }

    // Retrieve the nth available driver for a valid riderId
    @Test
    public void test_get_nth_match_success() throws MatchException {
        MatchingServiceImpl service = new MatchingServiceImpl();
        Driver driver1 = new Driver("driver1", new Location(0, 0));
        Driver driver2 = new Driver("driver2", new Location(1, 1));
        List<Driver> drivers = Arrays.asList(driver1, driver2);
        service.addMatch("rider1", drivers);
        assertEquals(driver2, service.getNthMatch("rider1", 2));
    }

    // Handle multiple matches for different riderIds correctly
    @Test
    public void test_multiple_rider_matches() {
        MatchingServiceImpl service = new MatchingServiceImpl();
        List<Driver> drivers1 = Collections.singletonList(new Driver("driver1", new Location(0, 0)));
        List<Driver> drivers2 = Collections.singletonList(new Driver("driver2", new Location(1, 1)));
        service.addMatch("rider1", drivers1);
        service.addMatch("rider2", drivers2);
        assertEquals(drivers1, service.matchRepository.getValue("rider1"));
        assertEquals(drivers2, service.matchRepository.getValue("rider2"));
    }

    // Attempt to retrieve a match for a non-existent riderId
    @Test(expected = RideNotFoundException.class)
    public void test_get_match_non_existent_rider() throws MatchException {
        MatchingServiceImpl service = new MatchingServiceImpl();
        service.getNthMatch("nonExistentRider", 1);
    }

    // Request the nth driver when fewer drivers are available
    @Test(expected = NotEnoughDriversException.class)
    public void test_request_nth_driver_insufficient_drivers() throws MatchException {
        MatchingServiceImpl service = new MatchingServiceImpl();
        List<Driver> drivers = Collections.singletonList(new Driver("driver1", new Location(0, 0)));
        service.addMatch("rider1", drivers);
        service.getNthMatch("rider1", 2);
    }

    // Retrieve a driver when the nth driver is not available
    @Test(expected = DriverNotAvailableException.class)
    public void test_get_unavailable_nth_driver() throws MatchException {
        MatchingServiceImpl service = new MatchingServiceImpl();
        Driver driver1 = new Driver("driver1", new Location(0, 0));
        driver1.setAvailable(false);
        List<Driver> drivers = Collections.singletonList(driver1);
        service.addMatch("rider1", drivers);
        service.getNthMatch("rider1", 1);
    }

    // Ensure that adding a match overwrites any existing match for the same riderId
    @Test
    public void test_overwrite_existing_match() {
        MatchingServiceImpl service = new MatchingServiceImpl();
        List<Driver> initialDrivers = Collections.singletonList(new Driver("driver1", new Location(0, 0)));
        List<Driver> newDrivers = Collections.singletonList(new Driver("driver2", new Location(1, 1)));
        service.addMatch("rider1", initialDrivers);
        service.addMatch("rider1", newDrivers);
        assertEquals(newDrivers, service.matchRepository.getValue("rider1"));
    }

    // Verify that the repository correctly stores and retrieves driver lists
    @Test
    public void test_repository_store_retrieve_drivers() {
        MatchRepository repository = new MatchRepository();
        List<Driver> drivers = Collections.singletonList(new Driver("driver1", new Location(0, 0)));
        repository.addValue("rider1", drivers);
        assertEquals(drivers, repository.getValue("rider1"));
    }

    // Test the behavior when the driver list is empty
    @Test(expected = NotEnoughDriversException.class)
    public void test_empty_driver_list() throws MatchException {
        MatchingServiceImpl service = new MatchingServiceImpl();
        List<Driver> emptyDrivers = Collections.emptyList();
        service.addMatch("rider1", emptyDrivers);
        service.getNthMatch("rider1", 1);
    }

    // Confirm that the repository handles null values gracefully
    @Test
    public void test_repository_handle_null_values() {
        MatchRepository repository = new MatchRepository();
        repository.addValue("rider1", null);
        assertNull(repository.getValue("rider1"));
    }

    // Validate the exception hierarchy and handling for match-related exceptions
    @Test
    public void test_exception_hierarchy_handling() {
        try {
            throw new RideNotFoundException();
        } catch (MatchException e) {
            assertTrue(e instanceof RideNotFoundException);
            assertTrue(e instanceof MatchException);
        }

        try {
            throw new NotEnoughDriversException();
        } catch (MatchException e) {
            assertTrue(e instanceof NotEnoughDriversException);
            assertTrue(e instanceof MatchException);
        }

        try {
            throw new DriverNotAvailableException();
        } catch (MatchException e) {
            assertTrue(e instanceof DriverNotAvailableException);
            assertTrue(e instanceof MatchException);
        }
    }
}