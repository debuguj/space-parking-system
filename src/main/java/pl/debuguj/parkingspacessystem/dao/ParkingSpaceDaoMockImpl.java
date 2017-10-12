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
    private static SimpleDateFormat timeDateFormat = new SimpleDateFormat(TIME_PATTERN);;

    static {
        try {
            listParkingSpaces.add(new ParkingSpace("12345",
                    timeDateFormat.parse("2017-10-12 10:25:48"),
                    timeDateFormat.parse("2017-10-12 10:35:12")));
            listParkingSpaces.add(new ParkingSpace("11212",
                    timeDateFormat.parse("2017-10-12 12:25:48"),
                    timeDateFormat.parse("2017-10-12 17:35:12")));
            listParkingSpaces.add(new ParkingSpace("12344",
                    timeDateFormat.parse("2017-10-12 15:25:48"),
                    timeDateFormat.parse("2017-10-12 16:35:12")));
            listParkingSpaces.add(new ParkingSpace("54345",
                    timeDateFormat.parse("2017-10-14 20:25:48"),
                    timeDateFormat.parse("2017-10-14 22:35:12")));
            listParkingSpaces.add(new ParkingSpace("12333",
                    timeDateFormat.parse("2017-10-14 11:15:48"),
                    timeDateFormat.parse("2017-10-14 21:35:12")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(ParkingSpace ps) {
        listParkingSpaces.add(ps);
    }

    @Override
    public ParkingSpace getParkingSpaceByRegistrationNo(String registrationNo) {
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
    public void changeParkingSpaceEndTime(String registrationNumber, Date timestamp){
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
