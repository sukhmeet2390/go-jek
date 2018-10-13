package com.gojek;

import com.gojek.consumers.FileConsumer;
import com.gojek.consumers.TerminalConsumer;
import com.gojek.service.DefaultParkingLot;
import com.gojek.service.ParkingLot;

import java.io.File;

public class ParkingLotRunner {
    public static void main(String[] args) {
        ParkingLot parkingLot = new DefaultParkingLot();
        if (args.length == 0) {
            new TerminalConsumer(parkingLot).execute();
        } else {
            String file = args[0].trim();
            new FileConsumer(new File(file), parkingLot).execute();
        }
    }
}
