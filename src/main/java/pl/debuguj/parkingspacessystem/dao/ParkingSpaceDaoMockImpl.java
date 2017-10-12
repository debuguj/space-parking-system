package pl.debuguj.parkingspacessystem.dao;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.domain.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.text.ParseException;
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
    public void changeStopParkingTimeAt(String registrationNumber, String timestamp){
        listParkingSpaces
                .forEach(ps -> {
                    if(registrationNumber.equals(ps.getCarRegistrationNumber())){
                        try {
                            ps.setEndTime(timestamp);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (IncorrectEndDateException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
