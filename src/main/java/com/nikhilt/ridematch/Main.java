package com.nikhilt.ridematch;

import com.nikhilt.ridematch.commands.AddDriver;
import com.nikhilt.ridematch.commands.AddRider;
import com.nikhilt.ridematch.commands.Bill;
import com.nikhilt.ridematch.commands.Match;
import com.nikhilt.ridematch.commands.StartRide;
import com.nikhilt.ridematch.commands.StopRide;
import com.nikhilt.ridematch.constants.CommandEnum;
import com.nikhilt.ridematch.services.IRideManager;
import com.nikhilt.ridematch.services.impl.RideManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // the file to be opened for reading
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            // returns true if there is another line to read
            IRideManager rideManager = new RideManager();
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(" ");
                CommandEnum command = CommandEnum.valueOf(line[0]);
                switch (command) {
                    case ADD_DRIVER:
                        rideManager.addDriver(new AddDriver(line));
                        break;
                    case ADD_RIDER:
                        rideManager.addRider(new AddRider(line));
                        break;
                    case MATCH:
                        System.out.println(rideManager.match(new Match(line)));
                        break;
                    case START_RIDE:
                        System.out.println(rideManager.startRide(new StartRide(line)));
                        break;
                    case STOP_RIDE:
                        System.out.println(rideManager.stopRide(new StopRide(line)));
                        break;
                    case BILL:
                        System.out.println(rideManager.generateBill(new Bill(line)));
                        break;
                }
                //Add your code here to process input commands
            }
            sc.close(); // closes the scanner
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
