package pl.debuguj.parkingspacessystem.services;

import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;

/**
 * Created by grzesiek on 09.10.17.
 */
public interface ParkingSpaceManagementService {

    /**
     As a driver, I want to start the parking meter, so I don’t have to pay
     the fine for the invalid parking
     */
    void reserveParkingSpace(ParkingSpace ps);

    /**
     As a parking operator, I want to check if the vehicle has started the parking meter
     */
    boolean checkVehicle(String registrationNumber);

    /**
     As a driver, I want to stop the parking meter, so that I pay only for the actual parking time
     */
    BigDecimal stopParkingMeter(String registrationNumber);

    /**
     As a driver, I want to know how much I have to pay for parking
     */
    BigDecimal checkFee(String startTime, String stopTime);

    /**
     As a parking owner, I want to know how much money was earned during a given day
     */
    BigDecimal getFeePerDay(String timestamp);

}
