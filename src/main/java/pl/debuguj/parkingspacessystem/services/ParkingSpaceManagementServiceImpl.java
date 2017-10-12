package pl.debuguj.parkingspacessystem.services;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    private PaymentService paymentService;

    @Override
    public BigDecimal reserveParkingSpace(ParkingSpace ps) {
        parkingSpaceDao.add(ps);
        return paymentService.getFee(ps);
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
    public BigDecimal stopParkingMeter(String registrationNumber, Date date) {

        parkingSpaceDao.changeParkingSpaceEndTime(registrationNumber, date);

        ParkingSpace ps = parkingSpaceDao.getAllParkingSpaces()
                .stream()
                .filter(parkingSpace -> registrationNumber.equals(parkingSpace.getCarRegistrationNumber()))
                .findFirst()
                .orElse(null);

        return paymentService.getFee(ps);
    }

    @Override
    public BigDecimal checkFee(Date startTime, Date stopTime) {
        return null;
    }

    @Override
    public BigDecimal getIncomePerDay(Date timestamp) throws ParseException {


        Date end = createEndDate(timestamp);

        return parkingSpaceDao.getAllParkingSpaces()
                .stream()
                .filter(ps -> timestamp.after(ps.getBeginTime())
                        && end.before(ps.getEndTime()))
                .map(ps -> paymentService.getFee(ps))
                .reduce(BigDecimal.ZERO, (a,b) -> a.add(b));

    }

    private Date createEndDate(Date d){
        return (new DateTime(d).plusDays(1)).toDate();
    }
}
