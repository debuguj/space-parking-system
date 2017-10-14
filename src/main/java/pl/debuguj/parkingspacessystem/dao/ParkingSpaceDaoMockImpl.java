package pl.debuguj.parkingspacessystem.dao;

import org.joda.time.DateTime;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.domain.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by grzesiek on 07.10.17.
 */
@Service
public class ParkingSpaceDaoMockImpl implements ParkingSpaceDao, ApplicationListener {

    private static List<ParkingSpace> listParkingSpaces = new ArrayList<>();

    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat timeDateFormat = new SimpleDateFormat(TIME_PATTERN);

    @Override
    public void add(final ParkingSpace parkingSpace) {

        listParkingSpaces.add(parkingSpace);
    }

    @Override
    public ParkingSpace findByRegistrationNo(final String registrationNo) {
        return listParkingSpaces
                .stream()
                .filter(ps -> registrationNo.equals(ps.getCarRegistrationNumber()))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<ParkingSpace> getAllItems() {
        return Collections.unmodifiableList(listParkingSpaces);
    }

    @Override
    public ParkingSpace changeEndTime(
            final String registrationNumber,
            final Date timestamp) throws IncorrectEndDateException {

        ParkingSpace ps = findByRegistrationNo(registrationNumber);

        if(!timestamp.after(ps.getBeginTime())) {
            throw new IncorrectEndDateException();
        }

        listParkingSpaces
                .forEach(s -> {
                    if (registrationNumber.equals(s.getCarRegistrationNumber())) {
                        s.setEndTime(timestamp);
                    }
                });

        return findByRegistrationNo(registrationNumber);
    }

    @Override
    public void removeAllItems() {
        listParkingSpaces.clear();
    }

    @Override
    public List<ParkingSpace> findByDate(final Date timestamp) {
        final Date end = createEndDate(timestamp);
        return Collections.unmodifiableList(
                listParkingSpaces
                .stream()
                .filter(ps -> timestamp.before(ps.getBeginTime())
                        && end.after(ps.getBeginTime()))
                .collect(Collectors.toList()));

    }

    private Date createEndDate(final Date d){
        return (new DateTime(d).plusDays(1)).toDate();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        try {
            listParkingSpaces.clear();

            listParkingSpaces.add(new ParkingSpace("11111",
                    timeDateFormat.parse("2017-10-13 10:25:48"),
                    timeDateFormat.parse("2017-10-13 10:35:12")));
            listParkingSpaces.add(new ParkingSpace("22222",
                    timeDateFormat.parse("2017-10-13 12:25:48"),
                    timeDateFormat.parse("2017-10-13 13:35:12")));
            listParkingSpaces.add(new ParkingSpace("33333",
                    timeDateFormat.parse("2017-10-13 15:25:48"),
                    timeDateFormat.parse("2017-10-13 16:35:12")));
            listParkingSpaces.add(new ParkingSpace("44444",
                    timeDateFormat.parse("2017-10-14 20:25:48"),
                    timeDateFormat.parse("2017-10-14 21:35:12")));
            listParkingSpaces.add(new ParkingSpace("55555",
                    timeDateFormat.parse("2017-10-14 11:15:48"),
                    timeDateFormat.parse("2017-10-14 12:35:12")));
        } catch (ParseException e){
            e.printStackTrace();
        } catch (IncorrectEndDateException e) {
            e.printStackTrace();
        }
    }
}
