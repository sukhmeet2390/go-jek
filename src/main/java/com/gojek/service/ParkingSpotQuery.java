package com.gojek.service;

import com.gojek.model.ParkingSpot;

import java.util.ArrayList;

public interface ParkingSpotQuery {
    public ParkingSpot getParkingSpotForRegistration(final String reg);

    public ArrayList<ParkingSpot> getParkingSpotsForColor(final String color);

    public ArrayList<String> getRegistrationNumberForColor(final String color);
}
