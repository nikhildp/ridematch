package com.nikhilt.ridematch.repositories;

import com.nikhilt.ridematch.entities.Driver;

import java.util.stream.Stream;

public class DriverRepository extends Repository<String, Driver> {
    public Stream<Driver> getDrivers() {
        return this.values.values().stream();
    }
}
