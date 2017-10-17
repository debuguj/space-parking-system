package pl.debuguj.parkingspacessystem.dao;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.exceptions.CarRegisteredInSystemException;
import pl.debuguj.parkingspacessystem.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.exceptions.ParkingSpaceNotFoundException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by grzesiek on 07.10.17.
 */
@Service
public class ParkingSpaceDaoMockImpl implements ParkingSpaceDao {

    private static List<ParkingSpace> listParkingSpaces = new ArrayList<>();

    @Override
    public void create(final ParkingSpace parkingSpace) throws CarRegisteredInSystemException {

        Optional<ParkingSpace> ps =
                findByRegistrationNo(parkingSpace.getCarRegistrationNumber());
        if(!ps.isPresent())
            listParkingSpaces.add(parkingSpace);
        else
            throw new CarRegisteredInSystemException();
    }

    @Override
    public Optional<ParkingSpace> findByRegistrationNo(final String registrationNo)  {
        return listParkingSpaces
                .stream()
                .filter(ps -> Objects.equals(registrationNo, ps.getCarRegistrationNumber()))
                .findFirst();
    }

    @Override
    public List<ParkingSpace> getAll() {
        return Collections.unmodifiableList(listParkingSpaces);
    }

    @Override
    public Optional<ParkingSpace> updateEndTime(
            final String registrationNumber,
            final Date timestamp) throws IncorrectEndDateException, ParkingSpaceNotFoundException {

        Optional<ParkingSpace> ps = findByRegistrationNo(registrationNumber);

        if(ps.isPresent())
        {
            if (!timestamp.after(ps.get().getBeginTime())) {
                throw new IncorrectEndDateException();
            }

            listParkingSpaces
                    .forEach(s -> {
                        if (Objects.equals(registrationNumber, s.getCarRegistrationNumber())) {
                            s.setEndTime(timestamp);
                        }
                    });

            return findByRegistrationNo(registrationNumber);
        }
        throw new ParkingSpaceNotFoundException();
    }

    @Override
    public void removeAll() {
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

//    @Override
//    public void onApplicationEvent(ApplicationEvent applicationEvent) {
//        try {
//            listParkingSpaces.clear();
//
//            listParkingSpaces.create(new ParkingSpace("11111",
//                    timeDateFormat.parse("2017-10-13 10:25:48"),
//                    timeDateFormat.parse("2017-10-13 10:35:12")));
//            listParkingSpaces.create(new ParkingSpace("22222",
//                    timeDateFormat.parse("2017-10-13 12:25:48"),
//                    timeDateFormat.parse("2017-10-13 13:35:12")));
//            listParkingSpaces.create(new ParkingSpace("33333",
//                    timeDateFormat.parse("2017-10-13 15:25:48"),
//                    timeDateFormat.parse("2017-10-13 16:35:12")));
//            listParkingSpaces.create(new ParkingSpace("44444",
//                    timeDateFormat.parse("2017-10-14 20:25:48"),
//                    timeDateFormat.parse("2017-10-14 21:35:12")));
//            listParkingSpaces.create(new ParkingSpace("55555",
//                    timeDateFormat.parse("2017-10-14 11:15:48"),
//                    timeDateFormat.parse("2017-10-14 12:35:12")));
//        } catch (ParseException e){
//            e.printStackTrace();
//        } catch (IncorrectEndDateException e) {
//            e.printStackTrace();
//        }
//    }
}
