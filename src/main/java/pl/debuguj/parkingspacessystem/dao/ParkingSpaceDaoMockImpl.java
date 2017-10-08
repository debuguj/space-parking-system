package pl.debuguj.parkingspacessystem.dao;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzesiek on 07.10.17.
 */
@Service
public class ParkingSpaceDaoMockImpl implements ParkingSpaceDao {

    private List<ParkingSpace> listParkingSpaces = new ArrayList<ParkingSpace>();

    @Override
    public void addParkingSpace(ParkingSpace ps) {
        listParkingSpaces.add(ps);
    }

    @Override
    public boolean checkSpace(ParkingSpace ps) {
        return listParkingSpaces.contains(ps);
    }

    @Override
    public List<ParkingSpace> getAllParkingSpaces() {
        return listParkingSpaces;
    }
}
