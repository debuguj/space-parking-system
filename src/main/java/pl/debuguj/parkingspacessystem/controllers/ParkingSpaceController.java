package pl.debuguj.parkingspacessystem.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.debuguj.parkingspacessystem.dao.ParkingSpaceDao;
import pl.debuguj.parkingspacessystem.domain.DriverType;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.services.ParkingSpaceManagementService;
import pl.debuguj.parkingspacessystem.services.PaymentService;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Created by grzesiek on 07.10.17.
 */
@RestController
public class ParkingSpaceController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingSpaceController.class);

    @Autowired
    private ParkingSpaceManagementService parkingSpaceManagement;

    @Autowired
    private PaymentService paymentService;

    public ParkingSpaceController(PaymentService paymentService, ParkingSpaceDao parkingSpaceDao) {
        this.paymentService = paymentService;
    }


    @GetMapping(value="/startMeter")
    public BigDecimal startParkingMeter(
            @RequestParam() String registrationNumber,
            @RequestParam() DriverType driverType,
            @RequestParam() String startTime,
            @RequestParam() String stopTime)  {

        try {

            ParkingSpace ps = new ParkingSpace(registrationNumber, driverType, startTime, stopTime);
            parkingSpaceManagement.reserveParkingSpace(ps);

            return paymentService.getFee(ps);

        } catch (ParseException e) {
            //TODO implement below
            return null;
        }
    }


    @GetMapping("/checkVehicle")
    public boolean checkVehicle(@RequestParam() String registrationNumber)
    {
        return parkingSpaceManagement.checkVehicle(registrationNumber);
    }


    @GetMapping("/stopParkingMeter")
    public void stopParkingMeter(@RequestParam String registrationNumber)
    {
        parkingSpaceManagement.stopParkingMeter(registrationNumber);
    }

    @GetMapping("/checkParkingFee")
    public void checkParkingFee(@RequestParam() String startTime, @RequestParam() String stopTime)
    {
        parkingSpaceManagement.checkFee(startTime, stopTime);
    }

    @GetMapping("/checkIncomePerDay")
    public void checkIncomePerDay(@RequestParam String date)
    {
        parkingSpaceManagement.getFeePerDay(date);
    }


}
