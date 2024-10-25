package com.nikhilt.ridematch.commands;

public class Match extends Command {
    String riderId;

    public Match(String[] input) {
        this.riderId = input[1];
    }

    public String getRiderId() {
        return riderId;
    }
}
