package com.nikhilt.ridematch.commands;

import com.nikhilt.ridematch.entities.Location;

public class AddRider extends Command {
    String riderId;
    Location location;

    public AddRider(String[] input) {
        this.riderId = input[1];
        this.location = new Location(Integer.parseInt(input[2]), Integer.parseInt(input[3]));
    }

    public AddRider(String riderId, Location location) {
        this.riderId = riderId;
        this.location = location;
    }

    public String getRiderId() {
        return riderId;
    }

    public Location getLocation() {
        return location;
    }
}
