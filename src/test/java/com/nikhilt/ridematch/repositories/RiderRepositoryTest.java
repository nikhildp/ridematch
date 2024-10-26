package com.nikhilt.ridematch.repositories;

import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.entities.Rider;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class RiderRepositoryTest {


    // Adding a rider to the repository and retrieving it by ID
    @Test
    public void test_add_and_retrieve_rider_by_id() {
        RiderRepository riderRepository = new RiderRepository();
        Rider rider = new Rider("rider1", new Location(0, 0));
        riderRepository.addValue(rider.getRiderId(), rider);

        Rider retrievedRider = riderRepository.getValue("rider1");
        assertNotNull(retrievedRider);
        assertEquals("rider1", retrievedRider.getRiderId());
    }

    // Checking if a rider exists in the repository
    @Test
    public void test_check_rider_exists() {
        RiderRepository riderRepository = new RiderRepository();
        Rider rider = new Rider("rider2", new Location(1, 1));
        riderRepository.addValue(rider.getRiderId(), rider);

        assertTrue(riderRepository.contains("rider2"));
    }
}