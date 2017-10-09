package pl.debuguj.parkingspacessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.dao.ParkingSpaceDao;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;
import java.util.List;

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
        //        Date currentTime = new Date();
//
//        return parkingSpaceDao.getAllParkingSpaces()
//                    .stream()
//                    .filter(parkingSpace -> registrationNumber.equals(parkingSpace.getCarRegistrationNumber()))
//                    .filter(parkingSpace -> {
//                        if (currentTime.after(parkingSpace.getBeginTime())
//                                && currentTime.before(parkingSpace.getEndTime()))
//                        {
//                            return true;
//                        }
//                            return false;
//                    })
//                    .findAny()
//                    .orElse(null);
        return false;
    }

    @Override
    public BigDecimal stopParkingMeter(String registrationNumber) {
        return null;
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
