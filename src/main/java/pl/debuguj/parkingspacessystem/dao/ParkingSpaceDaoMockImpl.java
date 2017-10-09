package pl.debuguj.parkingspacessystem.dao;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by grzesiek on 07.10.17.
 */
@Service
public class ParkingSpaceDaoMockImpl implements ParkingSpaceDao {

    private List<ParkingSpace> listParkingSpaces = new ArrayList<>();

    @Override
    public void add(ParkingSpace ps) {
        listParkingSpaces.add(ps);
    }

    @Override
    public List<ParkingSpace> getAllParkingSpaces() {
        return Collections.unmodifiableList(listParkingSpaces);
    }

    @Override
    public void changeStopParkingTimeAt(String registrationNumber, Date timestamp) {
        listParkingSpaces
                .forEach(ps -> {
                    if(registrationNumber.equals(ps.getCarRegistrationNumber())){
                        ps.setEndTime(timestamp);
                    }
                });
    }
}
