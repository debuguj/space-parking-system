package pl.debuguj.parkingspacessystem.dao;

import pl.debuguj.parkingspacessystem.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by grzesiek on 07.10.17.
 */
public interface ParkingSpaceDao {

    void add(final ParkingSpace parkingSpace);

    ParkingSpace findByRegistrationNo(final String registrationNo);

    List<ParkingSpace> getAllItems();

    ParkingSpace changeEndTime(final String registrationNumber, final Date date) throws IncorrectEndDateException;

    void removeAllItems();

    Collection<ParkingSpace> findByDate(final Date timestamp);
}
