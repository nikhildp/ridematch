package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Location;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class DriverServiceImplTest {


    // Adding a driver successfully stores the driver in the repository
    @Test
    public void test_add_driver_stores_in_repository() {
        DriverServiceImpl driverService = new DriverServiceImpl();
        Location location = new Location(10, 20);
        driverService.addDriver("driver1", location);
        Driver driver = driverService.driverRepository.getValue("driver1");
        assertNotNull(driver);
        assertEquals("driver1", driver.getDriverId());
        assertEquals(location, driver.getLocation());
    }

    // Updating a driver's availability changes the driver's state correctly
    @Test
    public void test_update_driver_availability() {
        DriverServiceImpl driverService = new DriverServiceImpl();
        Location location = new Location(10, 20);
        driverService.addDriver("driver1", location);
        driverService.updateDriverState("driver1", false);
        Driver driver = driverService.driverRepository.getValue("driver1");
        assertFalse(driver.isAvailable());
    }

    // Retrieving nearby drivers returns a list of drivers within the specified distance
    @Test
    public void test_get_nearby_drivers_within_distance() {
        DriverServiceImpl driverService = new DriverServiceImpl();
        Location location1 = new Location(10, 20);
        Location location2 = new Location(15, 25);
        driverService.addDriver("driver1", location1);
        driverService.addDriver("driver2", location2);
        List<Driver> drivers = driverService.getNearByDrivers(new Location(12, 22), 10, 2);
        assertEquals(2, drivers.size());
        assertTrue(drivers.stream().anyMatch(d -> d.getDriverId().equals("driver1")));
        assertTrue(drivers.stream().anyMatch(d -> d.getDriverId().equals("driver2")));
    }

    // Adding a driver with an existing ID overwrites the previous entry
    @Test
    public void test_add_driver_overwrites_existing() {
        DriverServiceImpl driverService = new DriverServiceImpl();
        Location location1 = new Location(10, 20);
        Location location2 = new Location(30, 40);
        driverService.addDriver("driver1", location1);
        driverService.addDriver("driver1", location2);
        Driver driver = driverService.driverRepository.getValue("driver1");
        assertEquals(location2, driver.getLocation());
    }

    // Updating a non-existent driver's state does not throw an error
    @Test
    public void test_update_non_existent_driver() {
        DriverServiceImpl driverService = new DriverServiceImpl();
        try {
            driverService.updateDriverState("nonExistentDriver", true);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    // Retrieving nearby drivers with zero distance returns only drivers at the exact location
    @Test
    public void test_get_nearby_drivers_zero_distance() {
        DriverServiceImpl driverService = new DriverServiceImpl();
        Location location1 = new Location(10, 20);
        Location location2 = new Location(15, 25);
        driverService.addDriver("driver1", location1);
        driverService.addDriver("driver2", location2);
        List<Driver> drivers = driverService.getNearByDrivers(location1, 0, 2);
        assertEquals(1, drivers.size());
        assertEquals("driver1", drivers.get(0).getDriverId());
    }
}