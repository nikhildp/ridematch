package com.nikhilt.ridematch.entities;

public class Driver {
    boolean available;
    private String driverId;
    private Location location;

    public Driver(String driverId, Location location) {
        this.driverId = driverId;
        this.location = location;
        this.available = true;
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
