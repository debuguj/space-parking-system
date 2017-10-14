package pl.debuguj.parkingspacessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.dao.ParkingSpaceDao;
import pl.debuguj.parkingspacessystem.domain.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    public BigDecimal reserveParkingSpace(final ParkingSpace ps) {
        parkingSpaceDao.add(ps);
        return paymentService.getFee(ps);
    }

    @Override
    public boolean checkVehicle(final String registrationNumber, final Date currentDate) {

        ParkingSpace ps = parkingSpaceDao.findByRegistrationNo(registrationNumber);

        if(null == ps)
            return false;
        return currentDate.after(ps.getBeginTime()) && currentDate.before(ps.getEndTime());


    }

    @Override
    public BigDecimal stopParkingMeter(final String registrationNumber, final Date date) throws IncorrectEndDateException {

        ParkingSpace ps = parkingSpaceDao.changeEndTime(registrationNumber, date);

        return paymentService.getFee(ps);
    }

    @Override
    public BigDecimal getIncomePerDay(final Date timestamp) {

        return parkingSpaceDao.findByDate(timestamp)
                .stream()
                .map(ps -> paymentService.getFee(ps))
                .reduce(BigDecimal.ZERO.setScale(1), (a,b) -> a.add(b).setScale(1));
    }

    @Override
    public int getReservedSpacesCount() {
        return parkingSpaceDao.getAllItems().size();
    }

    @Override
    public void removeAllParkingSpaces() {
        parkingSpaceDao.removeAllItems();
    }


}
