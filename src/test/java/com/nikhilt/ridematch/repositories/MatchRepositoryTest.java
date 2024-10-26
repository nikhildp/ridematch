package com.nikhilt.ridematch.repositories;

import com.nikhilt.ridematch.entities.Driver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MatchRepositoryTest {


    // Adding a new key-value pair to the repository
    @Test
    public void test_add_new_key_value_pair() {
        MatchRepository repository = new MatchRepository();
        List<Driver> drivers = new ArrayList<>();
        repository.addValue("route1", drivers);
        assertTrue(repository.contains("route1"));
    }

    // Retrieving a value using a valid key
    @Test
    public void test_retrieve_value_with_valid_key() {
        MatchRepository repository = new MatchRepository();
        List<Driver> drivers = new ArrayList<>();
        repository.addValue("route1", drivers);
        assertEquals(drivers, repository.getValue("route1"));
    }

    // Checking if a key exists in the repository
    @Test
    public void test_check_key_existence() {
        MatchRepository repository = new MatchRepository();
        repository.addValue("route1", new ArrayList<>());
        assertTrue(repository.contains("route1"));
        assertFalse(repository.contains("route2"));
    }

    // Attempting to retrieve a value with a non-existent key
    @Test
    public void test_retrieve_value_with_non_existent_key() {
        MatchRepository repository = new MatchRepository();
        assertNull(repository.getValue("nonExistentKey"));
    }

    // Removing a key that does not exist in the repository
    @Test
    public void test_remove_non_existent_key() {
        MatchRepository repository = new MatchRepository();
        assertFalse(repository.removeValue("nonExistentKey"));
    }
}