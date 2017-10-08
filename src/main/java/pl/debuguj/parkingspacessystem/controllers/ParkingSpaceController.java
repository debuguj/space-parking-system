package pl.debuguj.parkingspacessystem.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.debuguj.parkingspacessystem.dao.ParkingSpaceDao;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.calculation.Currency;
import pl.debuguj.parkingspacessystem.domain.DriverType;
import pl.debuguj.parkingspacessystem.calculation.PaymentManager;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by grzesiek on 07.10.17.
 */
@RestController
public class ParkingSpaceController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingSpaceController.class);

    private final PaymentManager paymentManager;

    private final ParkingSpaceDao parkingSpaceDao;

    public ParkingSpaceController(PaymentManager paymentManager, ParkingSpaceDao parkingSpaceDao) {
        this.paymentManager = paymentManager;
        this.parkingSpaceDao = parkingSpaceDao;

        //TO DO: change to read from application.properties
        paymentManager.setCurrency(Currency.PL);
    }

    /**
     As a driver, I want to start the parking meter, so I don’t have to pay
     the fine for the invalid parking
     */
    @GetMapping(value="/startMeter")
    public BigDecimal startParkingMeter(
            @RequestParam() String registrationNumber,
            @RequestParam() DriverType driverType,
            @RequestParam() Date beginTime,
            @RequestParam() Date endTime)
    {

        ParkingSpace ps = new ParkingSpace(registrationNumber, driverType, beginTime, endTime);
        parkingSpaceDao.addParkingSpace(ps);

        return paymentManager.getFee(ps);
    }

    /**
     As a parking operator, I want to check if the vehicle has started the parking meter
     */
    @GetMapping("/checkVehicle")
    public ParkingSpace checkVehicle(
            @RequestParam() String registrationNumber
    )
    {
        return parkingSpaceDao.getAllParkingSpaces()
                .stream()
                .filter(parkingSpace -> registrationNumber.equals(parkingSpace.getCarRegistrationNumber()))
                .findAny()
                .orElse(null);
    }

    /**
     As a driver, I want to stop the parking meter, so that I pay only for the actual parking time
     */
    public void stopParkingMeter(String registrationNumber)
    {

    }

    /**
     As a driver, I want to know how much I have to pay for parking
     */
    public void checkParkingFee(int period)
    {

    }

    /**
     As a parking owner, I want to know how much money was earned during a given day
     */
    public void checkIncomePerDay(Date date)
    {

    }


}
