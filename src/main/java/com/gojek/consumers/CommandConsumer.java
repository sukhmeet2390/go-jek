package com.gojek.consumers;

import com.gojek.exception.ParkingSpotNotAvailableException;
import com.gojek.model.Car;
import com.gojek.model.ParkingSpot;
import com.gojek.service.ParkingLot;

import java.util.ArrayList;

public class CommandConsumer {
    private final ParkingLot parkingLot;

    public CommandConsumer(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public enum COMMANDS {
        CREATE("create_parking_lot"),
        PARK("park"),
        UNPARK("leave"),
        STATUS("status"),
        REGISTRATION_BY_COLOR("registration_numbers_for_cars_with_colour"),
        SLOT_BY_COLOR("slot_numbers_for_cars_with_colour"),
        SLOT_BY_REGISTRATION("slot_number_for_registration_number");

        private String cmd;

        COMMANDS(String cmd) {
            this.cmd = cmd;
        }

        public static COMMANDS getValueOf(String cmd) {
            for (COMMANDS c : COMMANDS.values()) {
                if (c.getCmd().equals(cmd)) return c;
            }
            return null;
        }

        public String getCmd() {
            return cmd;
        }
    }

    public void handleCommand(final String command, final String[] args) {
        COMMANDS c = COMMANDS.getValueOf(command);
        if (c == null) return;
        switch (c) {
            case CREATE:
                int numberOfCars = Integer.valueOf(args[1]);
                handleCreate(numberOfCars);
                break;
            case PARK:
                String registrationNumber = args[1];
                String color = args[2];
                handlePark(registrationNumber, color);
                break;
            case UNPARK:
                int index = Integer.valueOf(args[1]);
                handleUnpark(index);
                break;
            case STATUS:
                handleStatus();
                break;
            case REGISTRATION_BY_COLOR:
                color = args[1];
                getRegistrationForColor(color);
                break;
            case SLOT_BY_COLOR:
                color = args[1];
                getSpotForColor(color);
                break;
            case SLOT_BY_REGISTRATION:
                registrationNumber = args[1];
                getSpotByRegistration(registrationNumber);
                break;
            default:
                break;
        }
    }

    private void getSpotByRegistration(String registrationNumber) {
        ParkingSpot value = parkingLot.getParkingSpotForRegistration(registrationNumber);
        if (value != null) {
            System.out.println(value.getSpotNumber() + 1);
        } else {
            System.out.println("Not found");
        }

    }

    private void getSpotForColor(String color) {
        ArrayList<Integer> parkingSpotsForColor = parkingLot.getParkingSpotsForColor(color);
        for (int i = 0; i < parkingSpotsForColor.size(); i++) {
            if (i != 0) System.out.print(",");
            System.out.print(parkingSpotsForColor.get(i) + 1);
        }
        System.out.println();
    }

    private void getRegistrationForColor(String color) {
        ArrayList<String> registrationNumberForColor = parkingLot.getRegistrationNumberForColor(color);
        for (int i = 0; i < registrationNumberForColor.size(); i++) {
            if (i != 0) System.out.print(",");
            System.out.print(registrationNumberForColor.get(i));
        }
        System.out.println();
    }

    private void handleStatus() {
        parkingLot.status();
    }

    private void handleUnpark(int index) {
        parkingLot.unpark(index-1);
        System.out.println("Slot number " + (index) + " is free");
    }

    private void handlePark(String registrationNumber, String color) {
        try {
            ParkingSpot park = parkingLot.park(new Car(registrationNumber, color));
            System.out.println("Allocated slot number: " + (park.getSpotNumber() + 1));
        } catch (ParkingSpotNotAvailableException e) {
            System.out.println("Sorry, parking lot is full");
        }
    }

    private void handleCreate(int numberOfCars) {
        parkingLot.create(numberOfCars);
        System.out.println("Created a parking lot with " + numberOfCars + " slots. ");
    }
}
