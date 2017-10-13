package pl.debuguj.parkingspacessystem.dao;

import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.util.Date;
import java.util.List;

/**
 * Created by grzesiek on 07.10.17.
 */
public interface ParkingSpaceDao {

    void add(final ParkingSpace parkingSpace);

    ParkingSpace findParkingSpaceByRegistrationNo(final String registrationNo);

    List<ParkingSpace> getAllParkingSpaces();

    ParkingSpace changeParkingSpaceEndTime(final String registrationNumber, final Date date);

    void removeAllItems();

    List<ParkingSpace> findParkingSpacesByDate(final Date timestamp);
}
