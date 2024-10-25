package com.nikhilt.ridematch.commands;

public class Bill extends Command {
    String rideId;

    public Bill(String[] input) {
        this.rideId = input[1];
    }

    public String getRideId() {
        return rideId;
    }
}
