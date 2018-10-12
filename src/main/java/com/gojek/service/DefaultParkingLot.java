package com.gojek.service;

import com.gojek.model.ParkingSpot;
import com.gojek.model.Vehicle;
import exception.ParkingSpotNotAvailableException;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DefaultParkingLot implements ParkingLot {
    private Map<ParkingSpot, Vehicle> parkedMap;
    private PriorityQueue<ParkingSpot> spots;
    private int availableSlot;

    public void create(int size) {
        this.availableSlot = size;
        parkedMap = new HashMap<ParkingSpot, Vehicle>(size);
        spots = new PriorityQueue<ParkingSpot>(size);
        for (int i = 0; i < spots.size(); i++) {
            spots.add(new ParkingSpot(i, null));
        }
    }

    public ParkingSpot park(Vehicle v) throws ParkingSpotNotAvailableException {
        synchronized (this) {
            if (availableSlot == 0) {
                throw new ParkingSpotNotAvailableException("No parking spot available !!");
            } else {
                final ParkingSpot slot = spots.poll();
                parkedMap.put(slot, v);
                availableSlot--;
                return slot;
            }
        }
    }

    public void unpark(ParkingSpot spot) {
        synchronized (this) {
            if (parkedMap.get(spot) != null) {
                parkedMap.remove(spot);
                availableSlot++;
                spots.add(spot);
            }
        }
    }

}
