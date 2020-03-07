package pl.debuguj.parkingspacessystem.spot;

import pl.debuguj.parkingspacessystem.spot.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.spot.exceptions.ParkingSpaceNotFoundException;
import pl.debuguj.parkingspacessystem.spot.exceptions.VehicleIsAlreadyActiveInSystemException;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by GB on 09.10.17.
 */
public interface SpaceManagementService {

    /**
     * As a driver, I want to start the parking meter, so I don’t have to pay
     * the fine for the invalid parking
     */

    boolean reserveParkingSpace(final Spot ps) throws VehicleIsAlreadyActiveInSystemException;

    /**
     As a parking operator, I want to check if the vehicle has started the parking meter
     */
    boolean checkVehicle(final String registrationNumber, final Date currentDate);

    /**
     * As a driver, I want to stop the parking meter, so that I pay only for the actual parking time
     * <p>
     * As a driver, I want to know how much I have to pay for parking
     */
    BigDecimal stopParkingMeter(final String registrationNumber, final Date date, final Currency currency) throws IncorrectEndDateException, ParkingSpaceNotFoundException;

    /**
     * As a parking owner, I want to know how much money was earned during a given day
     */
    BigDecimal getIncomePerDay(final Date timestamp, final Currency currency);
}