package pl.debuguj.parkingspacessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.dao.ParkingSpaceDao;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;
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
                        if (timestamp.after(parkingSpace.getBeginTime())
                                && timestamp.before(parkingSpace.getEndTime()))
                        {
                            return true;
                        }
                            return false;
                    })
                    .findFirst()
                    .get();

        return ps != null ? true : false;

    }

    @Override
    public BigDecimal stopParkingMeter(String registrationNumber) {
        Date timestamp = new Date();
        parkingSpaceDao.getAllParkingSpaces()
                .stream().forEach(ps -> {
                    if(registrationNumber.equals(ps.getCarRegistrationNumber())){
                        ps.setEndTime(timestamp);
                    }
        });
        ParkingSpace ps = parkingSpaceDao.getAllParkingSpaces()
                .stream()
                .filter(parkingSpace -> registrationNumber.equals(parkingSpace.getCarRegistrationNumber()))
                .findFirst()
                .orElse(null);
        return paymentService.getFee(ps);
    }

    @Override
    public BigDecimal checkFee(String startTime, String stopTime) {
        return null;
    }

    @Override
    public BigDecimal getFeePerDay(String timestamp) {
        return null;
    }
}
