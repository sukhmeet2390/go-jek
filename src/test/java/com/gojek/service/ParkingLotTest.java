package com.gojek.service;

import com.gojek.exception.ParkingSpotNotAvailableException;
import com.gojek.model.ParkingSpot;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.gojek.service.TestConstants.*;
import static org.junit.Assert.*;

public class ParkingLotTest {
    private static final int CAPACITY = 6;
    private ParkingLot parkingLot;

    @Before
    public void init() {
        parkingLot = new DefaultParkingLot();
        parkingLot.create(CAPACITY);
    }

    @Test
    public void shouldCreateParkingLot() {
        ParkingLot parkingLot = new DefaultParkingLot();
        parkingLot.create(CAPACITY);
        assertNotNull(parkingLot);
        assertEquals(CAPACITY, parkingLot.getCapacity());
    }

    @Test
    public void parkSuccess() {
        final ParkingSpot park = parkingLot.park(whiteCar);
        assertEquals(park.getSpotNumber(), 0);
    }

    @Test(expected = ParkingSpotNotAvailableException.class)
    public void parkFailure() {
        parkingLot.park(whiteCar);
        parkingLot.park(whiteCar2);
        parkingLot.park(redCar);
        parkingLot.park(redCar2);
        parkingLot.park(blueCar);
        parkingLot.park(greyCar);
        parkingLot.park(greyCar2);
    }

    @Test
    public void unparkSuccess() {
        final ParkingSpot park = parkingLot.park(whiteCar);
        parkingLot.unpark(park.getSpotNumber());
        final ParkingSpot val = parkingLot.getParkingSpotForRegistration(whiteCar.getRegistrationNumber());
        assertNull(val);
    }

    @Test
    public void getParkingSpotByRegistration() {
        ParkingSpot val = parkingLot.getParkingSpotForRegistration(whiteCar.getRegistrationNumber());
        assertNull(val);
        final ParkingSpot park = parkingLot.park(whiteCar);
        val = parkingLot.getParkingSpotForRegistration(whiteCar.getRegistrationNumber());
        assertNotNull(val);
        parkingLot.unpark(park.getSpotNumber());
        val = parkingLot.getParkingSpotForRegistration(whiteCar.getRegistrationNumber());
        assertNull(val);
    }

    @Test
    public void getParkingSpotByColor() {
        final ParkingSpot park = parkingLot.park(whiteCar);
        parkingLot.park(whiteCar2);
        final ParkingSpot red = parkingLot.park(redCar);
        final ArrayList<Integer> white = parkingLot.getParkingSpotsForColor("White");
        assertTrue(white.indexOf(park.getSpotNumber()) >= 0);
        assertTrue(white.indexOf(red.getSpotNumber()) == -1);
    }

    @Test
    public void getRegistrationByColor() {
        ParkingSpot park = parkingLot.park(whiteCar);
        parkingLot.park(whiteCar2);
        ParkingSpot red = parkingLot.park(redCar);
        ArrayList<String> white = parkingLot.getRegistrationNumberForColor("White");
        assertTrue(white.indexOf(park.getVehicle().getRegistrationNumber()) >= 0);
        assertTrue(white.indexOf(red.getVehicle().getRegistrationNumber()) == -1);
    }
}
