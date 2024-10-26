package com.nikhilt.ridematch.repositories;

import com.nikhilt.ridematch.entities.Ride;
import com.nikhilt.ridematch.entities.Rider;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class RideRepositoryTest {


    // Adding a Ride object with a unique key should store it in the repository
    @Test
    public void test_add_ride_with_unique_key() {
        RideRepository rideRepository = new RideRepository();
        Ride ride = new Ride("", null, new Rider("", null));
        String key = "uniqueKey";
        rideRepository.addValue(key, ride);
        assertTrue(rideRepository.contains(key));
    }

    // Retrieving a Ride object using its key should return the correct object
    @Test
    public void test_retrieve_ride_by_key() {
        RideRepository rideRepository = new RideRepository();
        Ride ride = new Ride("", null, new Rider("", null));
        String key = "rideKey";
        rideRepository.addValue(key, ride);
        assertEquals(ride, rideRepository.getValue(key));
    }


    // Checking if a key exists should return true if the key is present
    @Test
    public void test_key_existence_check() {
        RideRepository rideRepository = new RideRepository();
        String key = "existingKey";
        Ride ride = new Ride("", null, new Rider("", null));
        rideRepository.addValue(key, ride);
        assertTrue(rideRepository.contains(key));
    }

    // Retrieving a Ride object with a non-existent key should return null
    @Test
    public void test_retrieve_non_existent_key() {
        RideRepository rideRepository = new RideRepository();
        assertNull(rideRepository.getValue("nonExistentKey"));
    }

    // Removing a Ride object with a non-existent key should return false
    @Test
    public void test_remove_non_existent_key() {
        RideRepository rideRepository = new RideRepository();
        assertFalse(rideRepository.removeValue("nonExistentKey"));
    }


}