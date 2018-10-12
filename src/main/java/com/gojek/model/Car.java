package com.gojek.model;

import static com.gojek.model.VehicleType.CAR;

public class Car extends Vehicle {
    VehicleType type = CAR;

    public Car(String registrationNumber, String color) {
        super(registrationNumber, color);
    }

    public VehicleType getType() {
        return type;
    }
}
