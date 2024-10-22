package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.exceptions.match.DriverNotAvailableException;
import com.nikhilt.ridematch.exceptions.match.MatchException;
import com.nikhilt.ridematch.exceptions.match.NotEnoughDriversException;
import com.nikhilt.ridematch.exceptions.match.RideNotFoundException;
import com.nikhilt.ridematch.repositories.MatchRepository;
import com.nikhilt.ridematch.services.IMatchingService;

import java.util.List;

public class MatchingServiceImpl implements IMatchingService {
    MatchRepository matchRepository;

    public MatchingServiceImpl() {
        matchRepository = new MatchRepository();
    }


    @Override
    public void addMatch(String riderId, List<Driver> drivers) {
        matchRepository.addValue(riderId, drivers);
    }

    public Driver getNthMatch(String riderId, int n) throws MatchException {
        if (!matchRepository.contains(riderId)) {
            throw new RideNotFoundException();
        }
        List<Driver> drivers = matchRepository.getValue(riderId);
        if (drivers.size() < n) {
            throw new NotEnoughDriversException();
        }
        Driver driver = drivers.get(n);
        if (!driver.isAvailable()) {
            throw new DriverNotAvailableException();
        }
        return driver;
    }
}
