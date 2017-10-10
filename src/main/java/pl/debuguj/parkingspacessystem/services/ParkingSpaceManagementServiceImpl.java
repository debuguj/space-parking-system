package pl.debuguj.parkingspacessystem.services;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.calculations.PaymentManager;
import pl.debuguj.parkingspacessystem.dao.ParkingSpaceDao;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grzesiek on 09.10.17.
 */
@Service
public class ParkingSpaceManagementServiceImpl implements ParkingSpaceManagementService {

    @Autowired
    private ParkingSpaceDao parkingSpaceDao;

    @Override
    public void reserveParkingSpace(ParkingSpace ps) {
        parkingSpaceDao.add(ps);
    }

    @Override
    public boolean checkVehicle(String registrationNumber) {
        Date timestamp = new Date();

        ParkingSpace ps = parkingSpaceDao.getAllParkingSpaces()
                    .stream()
                    .filter(parkingSpace -> registrationNumber.equals(parkingSpace.getCarRegistrationNumber()))
                    .filter(parkingSpace -> {
                        return timestamp.after(parkingSpace.getBeginTime())
                                && timestamp.before(parkingSpace.getEndTime());
                    })
                    .findAny()
                    .orElse(null);

        return ps != null;

    }

    @Override
    public BigDecimal stopParkingMeter(String registrationNumber) {

        parkingSpaceDao.changeStopParkingTimeAt(registrationNumber, new Date());

        ParkingSpace ps = parkingSpaceDao.getAllParkingSpaces()
                .stream()
                .filter(parkingSpace -> registrationNumber.equals(parkingSpace.getCarRegistrationNumber()))
                .findFirst()
                .orElse(null);

        return PaymentManager.getFee(ps);
    }

    @Override
    public BigDecimal checkFee(String startTime, String stopTime) {
        return null;
    }

    @Override
    public BigDecimal getIncomePerDay(String timestamp) throws ParseException {

        Date begin = createBeginDate(timestamp);
        Date end = createEndDate(begin);

        return parkingSpaceDao.getAllParkingSpaces()
                .stream()
                .filter(ps -> {
                    return begin.after(ps.getBeginTime())
                        && end.before(ps.getEndTime());

                }).map(ParkingSpace::getFee)
                .reduce(BigDecimal.ZERO, (a,b) -> a.add(b));

    }


    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);

    private Date createBeginDate(String timestamp) throws ParseException {
        return simpleDateFormat.parse(timestamp);
    }

    private Date createEndDate(Date d){
        return (new DateTime(d).plusDays(1)).toDate();
    }
}
