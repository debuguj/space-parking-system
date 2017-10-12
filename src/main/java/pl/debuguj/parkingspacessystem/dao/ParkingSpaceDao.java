package pl.debuguj.parkingspacessystem.dao;

import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.util.Date;
import java.util.List;

/**
 * Created by grzesiek on 07.10.17.
 */
public interface ParkingSpaceDao {

    void add(ParkingSpace ps);

    List<ParkingSpace> getAllParkingSpaces();

    void changeStopParkingTimeAt(String registrationNumber, String date);
}
