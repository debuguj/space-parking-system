package pl.debuguj.parkingspacessystem.dao;

import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

/**
 * Created by grzesiek on 07.10.17.
 */
public interface ParkingSpaceDao {

    public void reserveSpace(ParkingSpace ps);
    public void checkSpace(ParkingSpace ps);
}
