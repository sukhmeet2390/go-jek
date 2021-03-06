package com.gojek.service;

import com.gojek.exception.ParkingSpotNotAvailableException;
import com.gojek.model.ParkingSpot;
import com.gojek.model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DefaultParkingLot implements ParkingLot {
    private Map<Integer, Vehicle> parkedMap;
    private Map<String, ParkingSpot> registrationMap;
    private PriorityQueue<ParkingSpot> spots;
    private int availableSlot;
    private int maxSize;

    public void create(int size) {
        this.availableSlot = size;
        this.maxSize = size;
        parkedMap = new HashMap<>(size);
        registrationMap = new HashMap<>(size);
        spots = new PriorityQueue<>(size);
        for (int i = 0; i < size; i++) {
            spots.add(new ParkingSpot(i, null));
        }
    }

    public int getCapacity() {
        return spots.size();
    }

    public ParkingSpot park(Vehicle v) throws ParkingSpotNotAvailableException {
        synchronized (this) {
            if (availableSlot == 0) {
                throw new ParkingSpotNotAvailableException("No parking spot available !!");
            } else {
                ParkingSpot parkingSpot = registrationMap.get(v.getRegistrationNumber());
                if (parkingSpot != null) return parkingSpot;
                final ParkingSpot slot = spots.poll();
                slot.setVehicle(v);
                parkedMap.put(slot.getSpotNumber(), v);
                registrationMap.put(v.getRegistrationNumber(), slot);
                availableSlot--;
                return slot;
            }
        }
    }

    public void unpark(int spotId) {
        synchronized (this) {
            if (parkedMap.get(spotId) != null) {
                Vehicle remove = parkedMap.remove(spotId);
                registrationMap.remove(remove.getRegistrationNumber());
                availableSlot++;
                spots.add(new ParkingSpot(spotId, null));
            }
        }
    }

    public void status() {
        if (this.parkedMap.size() > 0) {
            System.out.println("Slot No.\tRegistration No.\tColor");
            Vehicle car;
            for (int i = 0; i < maxSize; i++) {
                if (this.parkedMap.containsKey(i)) {
                    car = this.parkedMap.get(i);
                    System.out.println((i + 1) + "\t" + car.getRegistrationNumber() + "\t" + car.getColor());
                }
            }
        } else {
            System.out.println("Parking lot is empty");
        }
    }

    public ParkingSpot getParkingSpotForRegistration(String reg) {
        if (maxSize == 0) return null;
        return registrationMap.get(reg);
    }

    public ArrayList<Integer> getParkingSpotsForColor(String color) {
        ArrayList<Integer> values = new ArrayList<>();
        for (Map.Entry<Integer, Vehicle> entry : parkedMap.entrySet()) {
            if (entry.getValue().getColor().equalsIgnoreCase(color)) {
                values.add(entry.getKey());
            }
        }
        return values;
    }

    public ArrayList<String> getRegistrationNumberForColor(String color) {
        ArrayList<String> values = new ArrayList<>();
        for (Map.Entry<Integer, Vehicle> entry : parkedMap.entrySet()) {
            if (entry.getValue().getColor().equalsIgnoreCase(color)) {
                values.add(entry.getValue().getRegistrationNumber());
            }
        }
        return values;
    }
}
