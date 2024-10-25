package com.nikhilt.ridematch.commands;

import com.nikhilt.ridematch.entities.Location;

public class StopRide extends Command {
    String rideId;
    Location location;
    int timeTaken;

    public StopRide(String[] input) {
        this.rideId = input[1];
        this.location = new Location(Integer.parseInt(input[2]), Integer.parseInt(input[3]));
        this.timeTaken = Integer.parseInt(input[4]);
    }

    public String getRideId() {
        return rideId;
    }

    public Location getLocation() {
        return location;
    }

    public int getTimeTaken() {
        return timeTaken;
    }
}
