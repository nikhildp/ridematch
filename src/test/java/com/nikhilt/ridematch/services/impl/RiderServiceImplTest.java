package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.entities.Rider;
import com.nikhilt.ridematch.repositories.RiderRepository;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class RiderServiceImplTest {


    // Adding a rider successfully stores the rider in the repository
    @Test
    public void test_add_rider_stores_successfully() {
        RiderServiceImpl riderService = new RiderServiceImpl();
        Location location = new Location(10, 20);
        riderService.addRider("rider1", location);
        Rider rider = riderService.getRider("rider1");
        assertNotNull(rider);
        assertEquals("rider1", rider.getRiderId());
        assertEquals(location, rider.getLocation());
    }

    // Retrieving a rider returns the correct Rider object
    @Test
    public void test_retrieve_rider_returns_correct_object() {
        RiderServiceImpl riderService = new RiderServiceImpl();
        Location location = new Location(10, 20);
        riderService.addRider("rider1", location);
        Rider rider = riderService.getRider("rider1");
        assertNotNull(rider);
        assertEquals("rider1", rider.getRiderId());
        assertEquals(location, rider.getLocation());
    }

    // Adding multiple riders stores all riders correctly
    @Test
    public void test_add_multiple_riders_stores_correctly() {
        RiderServiceImpl riderService = new RiderServiceImpl();
        Location location1 = new Location(10, 20);
        Location location2 = new Location(30, 40);
        riderService.addRider("rider1", location1);
        riderService.addRider("rider2", location2);
        assertNotNull(riderService.getRider("rider1"));
        assertNotNull(riderService.getRider("rider2"));
    }

    // Retrieving a non-existent rider returns null
    @Test
    public void test_retrieve_non_existent_rider_returns_null() {
        RiderServiceImpl riderService = new RiderServiceImpl();
        Rider rider = riderService.getRider("nonExistentRider");
        assertNull(rider);
    }

    // The RiderServiceImpl initializes with a new RiderRepository
    @Test
    public void test_initialization_with_new_repository() {
        RiderServiceImpl riderService = new RiderServiceImpl();
        assertNotNull(riderService.riderRepository);
    }


    // Ensure RiderRepository is correctly instantiated within RiderServiceImpl
    @Test
    public void test_repository_instantiation_in_service_impl() {
        RiderServiceImpl riderService = new RiderServiceImpl();
        assertNotNull(riderService.riderRepository);
        assertTrue(riderService.riderRepository instanceof RiderRepository);
    }

    // Verify that Rider objects are correctly constructed before storage
    @Test
    public void test_rider_object_construction_before_storage() throws NoSuchFieldException, IllegalAccessException {
        RiderRepository mockRepository = mock(RiderRepository.class);
        doAnswer(invocation -> {
            String id = invocation.getArgument(0);
            Rider rider = invocation.getArgument(1);
            assertEquals("rider1", id);
            assertNotNull(rider);
            assertEquals("rider1", rider.getRiderId());
            return null;
        }).when(mockRepository).addValue(anyString(), any(Rider.class));

        RiderServiceImpl riderService = new RiderServiceImpl();
        Field field = RiderServiceImpl.class.getDeclaredField("riderRepository");
        field.setAccessible(true);
        field.set(riderService, mockRepository);

        Location location = new Location(10, 20);
        riderService.addRider("rider1", location);

        verify(mockRepository).addValue(eq("rider1"), any(Rider.class));
    }
}