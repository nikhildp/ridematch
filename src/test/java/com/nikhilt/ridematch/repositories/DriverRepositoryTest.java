package com.nikhilt.ridematch.repositories;

import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Location;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class DriverRepositoryTest {


    // Adding a driver to the repository and retrieving it by ID
    @Test
    public void test_add_and_retrieve_driver_by_id() {
        DriverRepository driverRepository = new DriverRepository();
        Driver driver = new Driver("driver1", new Location(0, 0));
        driverRepository.addValue(driver.getDriverId(), driver);

        Driver retrievedDriver = driverRepository.getValue("driver1");
        assertNotNull(retrievedDriver);
        assertEquals("driver1", retrievedDriver.getDriverId());
    }

    // Checking if a driver exists in the repository
    @Test
    public void test_check_driver_exists() {
        DriverRepository driverRepository = new DriverRepository();
        Driver driver = new Driver("driver2", new Location(1, 1));
        driverRepository.addValue(driver.getDriverId(), driver);

        assertTrue(driverRepository.contains("driver2"));
    }
}