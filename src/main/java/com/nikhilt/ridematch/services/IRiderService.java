package com.nikhilt.ridematch.services;

import com.nikhilt.ridematch.entities.Rider;

public interface IRiderService {
    void addRider(String riderId, int x, int y);
    Rider getRider(String riderId);
}
