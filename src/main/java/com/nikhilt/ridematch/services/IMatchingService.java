package com.nikhilt.ridematch.services;

import com.nikhilt.ridematch.entities.Driver;
import com.nikhilt.ridematch.exceptions.match.MatchException;

import java.util.List;

public interface IMatchingService {

    void addMatch(String riderId, List<Driver> drivers);

    Driver getNthMatch(String riderId, int n) throws MatchException;
}
