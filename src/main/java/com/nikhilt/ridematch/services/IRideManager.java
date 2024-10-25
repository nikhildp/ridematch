package com.nikhilt.ridematch.services;

import com.nikhilt.ridematch.commands.AddDriver;
import com.nikhilt.ridematch.commands.AddRider;
import com.nikhilt.ridematch.commands.Bill;
import com.nikhilt.ridematch.commands.Match;
import com.nikhilt.ridematch.commands.StartRide;
import com.nikhilt.ridematch.commands.StopRide;

public abstract class IRideManager {

    public abstract void addDriver(AddDriver addDriver);
    public abstract void addRider(AddRider addRider);
    public abstract String match(Match match);
    public abstract String startRide(StartRide startRide);
    public abstract String stopRide(StopRide stopRide);
    public abstract String generateBill(Bill bill);
}
