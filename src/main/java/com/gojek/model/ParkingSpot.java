package com.gojek.model;

public class ParkingSpot implements Comparable<ParkingSpot> {
    private Long spotNumber;
    private boolean isVacant;
    private Vehicle vehicle;
    private ParkingSpotSize spotSize = ParkingSpotSize.MEDIUM;

    public ParkingSpot(Long spotNumber, boolean isVacant, Vehicle vehicle, ParkingSpotSize spotSize) {
        this.spotNumber = spotNumber;
        this.isVacant = isVacant;
        this.vehicle = vehicle;
        this.spotSize = spotSize;
    }

    public Long getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(Long spotNumber) {
        this.spotNumber = spotNumber;
    }

    public boolean isVacant() {
        return isVacant;
    }

    public void setVacant(boolean vacant) {
        isVacant = vacant;
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
        return this.spotNumber.compareTo(spot.spotNumber);
    }
}
