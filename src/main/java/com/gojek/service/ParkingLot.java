package com.gojek.service;

import com.gojek.model.ParkingSpot;
import com.gojek.model.Vehicle;
import exception.ParkingSpotNotAvailableException;

public interface ParkingLot {
    public ParkingSpot park(Vehicle v) throws ParkingSpotNotAvailableException;

    public void unpark(ParkingSpot spot);

    public void create(int size);
}
