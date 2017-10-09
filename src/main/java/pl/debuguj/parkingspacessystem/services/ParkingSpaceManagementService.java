package pl.debuguj.parkingspacessystem.services;

import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.util.List;

/**
 * Created by grzesiek on 09.10.17.
 */
public interface ParkingSpaceManagementService {

    public void reserveParkingSpace(ParkingSpace ps);

    public List<ParkingSpace> getAllParkingSpaces();
}
