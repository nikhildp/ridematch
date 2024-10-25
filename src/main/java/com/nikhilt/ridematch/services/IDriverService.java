package com.nikhilt.ridematch.services;

import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Location;

import java.util.List;

public interface IDriverService {
    void addDriver(String driverId, Location location);

    void updateDriverState(String driverId, boolean available);

    List<Driver> getNearByDrivers(Location location, Integer distance, Integer count);
}
