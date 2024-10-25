package com.nikhilt.ridematch.commands;

import com.nikhilt.ridematch.entities.Location;

public class AddDriver extends Command {
    String driverId;
    Location location;

    public AddDriver(String[] input) {
        this.driverId = input[1];
        this.location = new Location(Integer.parseInt(input[2]), Integer.parseInt(input[3]));
    }

    public String getDriverId() {
        return driverId;
    }

    public Location getLocation() {
        return location;
    }
}
