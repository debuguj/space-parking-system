package pl.debuguj.parkingspacessystem.dao;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.domain.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by grzesiek on 07.10.17.
 */
@Service
public class ParkingSpaceDaoMockImpl implements ParkingSpaceDao {

    private static List<ParkingSpace> listParkingSpaces = new ArrayList<>();

    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat timeDateFormat = new SimpleDateFormat(TIME_PATTERN);

    @Override
    public void add(final ParkingSpace parkingSpace) {
        listParkingSpaces.add(parkingSpace);
    }

    @Override
    public ParkingSpace getParkingSpaceByRegistrationNo(final String registrationNo) {
        return listParkingSpaces
                .stream()
                .filter(ps -> registrationNo.equals(ps.getCarRegistrationNumber()))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<ParkingSpace> getAllParkingSpaces() {
        return Collections.unmodifiableList(listParkingSpaces);
    }

    @Override
    public void changeParkingSpaceEndTime(final String registrationNumber, final Date timestamp){
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

    @Override
    public void removeAllItems() {
        listParkingSpaces.clear();
    }


}
