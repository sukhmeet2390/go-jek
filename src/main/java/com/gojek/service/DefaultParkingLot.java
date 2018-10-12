package com.gojek.service;

import com.gojek.model.ParkingSpot;
import com.gojek.model.Vehicle;
import exception.ParkingSpotNotAvailableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DefaultParkingLot implements ParkingLot, ParkingSpotQuery {
    private Map<ParkingSpot, Vehicle> parkedMap;
    private Map<String, ParkingSpot> registrationMap;
    private PriorityQueue<ParkingSpot> spots;
    private int availableSlot;

    public void create(int size) {
        this.availableSlot = size;
        parkedMap = new HashMap<ParkingSpot, Vehicle>(size);
        registrationMap = new HashMap<String, ParkingSpot>(size);
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
                registrationMap.put(v.getRegistrationNumber(), slot);
                availableSlot--;
                return slot;
            }
        }
    }

    public void unpark(ParkingSpot spot) {
        synchronized (this) {
            if (parkedMap.get(spot) != null) {
                parkedMap.remove(spot);
                registrationMap.remove(spot.getVehicle().getRegistrationNumber());
                availableSlot++;
                spots.add(spot);
            }
        }
    }

    public ParkingSpot getParkingSpotForRegistration(String reg) {
        return registrationMap.get(reg);
    }

    public ArrayList<ParkingSpot> getParkingSpotsForColor(String color) {
        ArrayList<ParkingSpot> values = new ArrayList<ParkingSpot>();
        for (Map.Entry<ParkingSpot, Vehicle> entry : parkedMap.entrySet()) {
            if (entry.getValue().getColor().equalsIgnoreCase(color)) {
                values.add(entry.getKey());
            }
        }
        return values;
    }

    public ArrayList<String> getRegistrationNumberForColor(String color) {
        ArrayList<String> values = new ArrayList<String>();
        for (Map.Entry<ParkingSpot, Vehicle> entry : parkedMap.entrySet()) {
            if (entry.getValue().getColor().equalsIgnoreCase(color)) {
                values.add(entry.getValue().getRegistrationNumber());
            }
        }
        return values;
    }
}
