package pl.debuguj.parkingspacessystem.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.debuguj.parkingspacessystem.dao.ParkingSpaceDao;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.domain.Currency;
import pl.debuguj.parkingspacessystem.domain.DriverType;
import pl.debuguj.parkingspacessystem.services.ParkingSpaceManagementService;
import pl.debuguj.parkingspacessystem.services.PaymentService;
import pl.debuguj.parkingspacessystem.services.PaymentServiceImpl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grzesiek on 07.10.17.
 */
@RestController
public class ParkingSpaceController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingSpaceController.class);

    @Autowired
    private ParkingSpaceManagementService parkingSpaceMangement;

    @Autowired
    private PaymentService paymentService;

    public ParkingSpaceController(PaymentService paymentService, ParkingSpaceDao parkingSpaceDao) {
        this.paymentService = paymentService;
    }

    /**
     As a driver, I want to start the parking meter, so I don’t have to pay
     the fine for the invalid parking
     */
    @GetMapping(value="/startMeter")
    public BigDecimal startParkingMeter(
            @RequestParam() String registrationNumber,
            @RequestParam() DriverType driverType,
            @RequestParam() String startTime,
            @RequestParam() String stopTime)  {

        try {

            ParkingSpace ps = new ParkingSpace(registrationNumber, driverType, startTime, stopTime);
            parkingSpaceMangement.reserveParkingSpace(ps);

            return paymentService.getFee(ps);

        } catch (ParseException e) {
            //TODO implement below
            return null;
        }
    }

    /**
     As a parking operator, I want to check if the vehicle has started the parking meter
     */
    @GetMapping("/checkVehicle")
    public ParkingSpace checkVehicle(
            @RequestParam() String registrationNumber
    )
    {

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
        return null;
    }

    /**
     As a driver, I want to stop the parking meter, so that I pay only for the actual parking time
     */
    @GetMapping("/stopParkingMeter")
    public void stopParkingMeter(@RequestParam String registrationNumber)
    {

        parkingSpaceMangement.getAllParkingSpaces();
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
    public void checkIncomePerDay(String date)
    {

    }


}
