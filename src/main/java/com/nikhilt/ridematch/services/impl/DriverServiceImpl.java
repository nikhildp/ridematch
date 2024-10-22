package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.repositories.DriverRepository;
import com.nikhilt.ridematch.services.IDriverService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;

public class DriverServiceImpl implements IDriverService {
    DriverRepository driverRepository;

    @Override
    public void addDriver(String driverId, int x, int y) {
        driverRepository.addValue(driverId, new Driver(driverId, new Location(x, y)));
    }

    public void updateDriverState(String driverId, boolean available) {
        if (driverRepository.contains(driverId)) {
            driverRepository.getValue(driverId).setAvailable(available);
        }
    }

    @Override
    public List<Driver> getNearByDrivers(Location location, Integer distance, Integer count) {
        Stream<Driver> driverStream = driverRepository.getDrivers();
        Queue<Driver> closest = new PriorityQueue<>((d1, d2) -> {
            int compare = Double.compare(d2.getLocation().getDistance(location), d1.getLocation().getDistance(location));
            if (compare == 0) {
                return d2.getDriverId().compareTo(d1.getDriverId());
            }
            return compare;
        });
        driverStream.filter(driver -> driver.isAvailable() && driver.getLocation().getDistance(location) <= distance)
                .forEach(driver -> {
                            closest.add(driver);
                            if (closest.size() > count) {
                                closest.poll();
                            }
                        }
                );
        List<Driver> drivers = new ArrayList<>();
        while (!closest.isEmpty()) {
            drivers.add(closest.poll());
        }
        Collections.reverse(drivers);
        return drivers;
    }
}
