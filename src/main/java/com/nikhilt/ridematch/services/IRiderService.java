package com.nikhilt.ridematch.services;

import com.nikhilt.ridematch.entities.Location;
import com.nikhilt.ridematch.entities.Rider;

public interface IRiderService {
    void addRider(String riderId, Location location);

    Rider getRider(String riderId);
}
