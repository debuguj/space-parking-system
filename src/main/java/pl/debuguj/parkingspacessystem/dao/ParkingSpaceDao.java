package pl.debuguj.parkingspacessystem.dao;

import pl.debuguj.parkingspacessystem.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.exceptions.ParkingSpaceNotFoundException;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by grzesiek on 07.10.17.
 */
public interface ParkingSpaceDao {

    void create(final ParkingSpace parkingSpace);

    Optional<ParkingSpace> findByRegistrationNo(final String registrationNo);

    List<ParkingSpace> getAll();

    Optional<ParkingSpace> updateEndTime(final String registrationNumber, final Date date) throws IncorrectEndDateException, ParkingSpaceNotFoundException;

    void removeAll();

    Collection<ParkingSpace> findByDate(final Date timestamp);
}
