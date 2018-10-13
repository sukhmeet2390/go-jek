package com.gojek.service;

import com.gojek.model.ParkingSpot;
import com.gojek.model.Vehicle;
import com.gojek.exception.ParkingSpotNotAvailableException;

import java.util.ArrayList;

public interface ParkingLot {
    ParkingSpot park(Vehicle v) throws ParkingSpotNotAvailableException;

    void unpark(int spotId);

    void create(int size);

    void status();

    ParkingSpot getParkingSpotForRegistration(final String reg);

    ArrayList<Integer> getParkingSpotsForColor(final String color);

    ArrayList<String> getRegistrationNumberForColor(final String color);
}
