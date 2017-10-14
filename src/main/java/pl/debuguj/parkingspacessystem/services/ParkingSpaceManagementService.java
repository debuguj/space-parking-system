package pl.debuguj.parkingspacessystem.services;

import pl.debuguj.parkingspacessystem.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by grzesiek on 09.10.17.
 */
public interface ParkingSpaceManagementService {

    /**
     As a driver, I want to start the parking meter, so I don’t have to pay
     the fine for the invalid parking
     */
    /**
     As a driver, I want to know how much I have to pay for parking
     */
    BigDecimal reserveParkingSpace(final ParkingSpace ps);

    /**
     As a parking operator, I want to check if the vehicle has started the parking meter
     */
    boolean checkVehicle(final String registrationNumber, final Date currentDate);

    /**
     As a driver, I want to stop the parking meter, so that I pay only for the actual parking time
     */
    BigDecimal stopParkingMeter(final String registrationNumber, final Date date) throws IncorrectEndDateException;

    /**
     As a parking owner, I want to know how much money was earned during a given day
     */
    BigDecimal getIncomePerDay(final Date timestamp);

    int getReservedSpacesCount();

    void removeAllParkingSpaces();

}
