package com.nikhilt.ridematch.services.impl;

import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.entities.Rider;
import com.nikhilt.ridematch.repositories.RiderRepository;
import com.nikhilt.ridematch.services.IRiderService;

public class RiderServiceImpl implements IRiderService {
    RiderRepository riderRepository;

    public RiderServiceImpl() {
        this.riderRepository = new RiderRepository();
    }

    @Override
    public void addRider(String riderId, int x, int y) {
        riderRepository.addValue(riderId, new Rider(riderId, new Location(x, y)));
    }

    @Override
    public Rider getRider(String riderId) {
        return riderRepository.getValue(riderId);
    }
}
