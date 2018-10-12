package com.gojek.model;

public class ParkingSpot implements Comparable<ParkingSpot> {
    private int spotNumber;
    private boolean isVacant;
    private Vehicle vehicle;
    private ParkingSpotSize spotSize = ParkingSpotSize.MEDIUM;

    public ParkingSpot(int spotNumber, Vehicle vehicle) {
        this.spotNumber = spotNumber;
        this.vehicle = vehicle;
    }

    public ParkingSpot(int spotNumber, Vehicle vehicle, ParkingSpotSize spotSize) {
        this.spotNumber = spotNumber;
        this.vehicle = vehicle;
        this.spotSize = spotSize;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public boolean isVacant() {
        return this.vehicle == null;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSpotSize getSpotSize() {
        return spotSize;
    }

    public void setSpotSize(ParkingSpotSize spotSize) {
        this.spotSize = spotSize;
    }

    public int compareTo(ParkingSpot spot) {
        return this.spotNumber - spot.spotNumber;
    }
}
