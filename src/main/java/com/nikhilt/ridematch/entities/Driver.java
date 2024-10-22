package com.nikhilt.ridematch.entities;

public class Driver {
    private String driverId;
    private Location location;
    boolean available;

    public Driver(String driverId, Location location) {
        this.driverId = driverId;
        this.location = location;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
