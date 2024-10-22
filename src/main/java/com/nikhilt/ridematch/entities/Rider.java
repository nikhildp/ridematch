package com.nikhilt.ridematch.entities;

public class Rider {
    private String riderId;
    private Location location;

    public Rider(String riderId, Location location) {
        this.riderId = riderId;
        this.location = location;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
