package pl.debuguj.parkingspacessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.dao.ParkingSpaceDao;
import pl.debuguj.parkingspacessystem.domain.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;
import java.text.ParseException;
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
    public boolean checkVehicle(String registrationNumber, Date currentDate) {

        ParkingSpace ps = parkingSpaceDao.findParkingSpaceByRegistrationNo(registrationNumber);

        return currentDate.after(ps.getBeginTime()) && currentDate.before(ps.getEndTime());
    }

    @Override
    public BigDecimal stopParkingMeter(String registrationNumber, Date date) throws IncorrectEndDateException {

        ParkingSpace ps = parkingSpaceDao.changeParkingSpaceEndTime(registrationNumber, date);

        return paymentService.getFee(ps);
    }

    @Override
    public BigDecimal getIncomePerDay(Date timestamp) throws ParseException {

        return parkingSpaceDao.findParkingSpacesByDate(timestamp)
                .stream()
                .map(ps -> paymentService.getFee(ps))
                .reduce(BigDecimal.ZERO, (a,b) -> a.add(b));
    }

    @Override
    public int getReservedSpacesCount() {
        return parkingSpaceDao.getAllParkingSpaces().size();
    }

    @Override
    public void removeAllParkingSpaces() {
        parkingSpaceDao.removeAllItems();
    }


}
