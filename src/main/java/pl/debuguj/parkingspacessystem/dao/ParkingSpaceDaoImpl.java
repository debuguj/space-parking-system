package pl.debuguj.parkingspacessystem.dao;

import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzesiek on 07.10.17.
 */
public class ParkingSpaceDaoImpl implements ParkingSpaceDao {

    private List<ParkingSpace> listParkingSpaces = new ArrayList<ParkingSpace>();

    @Override
    public void reserveSpace(ParkingSpace ps) {
        listParkingSpaces.add(ps);
    }

    @Override
    public void checkSpace(ParkingSpace ps) {
        listParkingSpaces.contains(ps);
    }
}
