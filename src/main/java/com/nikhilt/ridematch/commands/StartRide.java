package com.nikhilt.ridematch.commands;

public class StartRide extends Command {
    String rideId;
    int n;
    String riderId;

    public StartRide(String[] input) {
        this.rideId = input[1];
        this.n = Integer.parseInt(input[2]);
        this.riderId = input[3];
    }

    public String getRideId() {
        return rideId;
    }

    public int getN() {
        return n;
    }

    public String getRiderId() {
        return riderId;
    }
}
