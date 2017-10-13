package pl.debuguj.parkingspacessystem.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.debuguj.parkingspacessystem.enums.DriverType;
import pl.debuguj.parkingspacessystem.domain.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.services.ParkingSpaceManagementService;
import pl.debuguj.parkingspacessystem.services.PaymentService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grzesiek on 07.10.17.
 */
@RestController
public class ParkingSpaceController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingSpaceController.class);

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);

    private static final String TIME_PATTERN = "yyyy-MM-dd";
    private static final SimpleDateFormat dayDateFormat = new SimpleDateFormat(TIME_PATTERN);

    public static final String URI_START_METER = "/startMeter";
    public static final String URI_CHECK_VEHICLE = "/checkVehicle";
    public static final String URI_STOP_METER = "/stopMeter";
    public static final String URI_CHECK_INCOME_PER_DAY = "/checkIncomePerDay";

    private final ParkingSpaceManagementService parkingSpaceManagement;


    public ParkingSpaceController(ParkingSpaceManagementService parkingSpaceManagement) {
        this.parkingSpaceManagement = parkingSpaceManagement;

    }

    @GetMapping(value=URI_START_METER)
    public BigDecimal startParkingMeter(
            @RequestParam() final String registrationNumber,
            @RequestParam() final DriverType driverType,
            @RequestParam() final String startTime,
            @RequestParam() final String stopTime)  {

        try {

            Date begin = simpleDateFormat.parse(startTime);
            Date end = simpleDateFormat.parse(stopTime);
            ParkingSpace ps = new ParkingSpace(registrationNumber, begin, end);
            ps.setDriverType(driverType);

            return parkingSpaceManagement.reserveParkingSpace(ps);
        } catch (ParseException e) {
            //TODO implement below
            return null;
        } catch (IncorrectEndDateException e) {
            //TODO implement below
            return null;
        }
    }

    @GetMapping(URI_CHECK_VEHICLE)
    public boolean checkVehicle(@RequestParam final String registrationNumber,
                                @RequestParam final String currentDate)
    {

        try {
            Date date = simpleDateFormat.parse(currentDate);
            return parkingSpaceManagement.checkVehicle(registrationNumber, date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    @GetMapping(URI_STOP_METER)
    public BigDecimal stopParkingMeter(@RequestParam final String registrationNumber,
                                       @RequestParam final String timeStamp)
    {
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStamp);
            return parkingSpaceManagement.stopParkingMeter(registrationNumber, date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    @GetMapping(URI_CHECK_INCOME_PER_DAY)
    public BigDecimal checkIncomePerDay(@RequestParam final String date)
    {
        try {
            BigDecimal sum = parkingSpaceManagement.getIncomePerDay(dayDateFormat.parse(date));
            return sum;
        } catch (ParseException e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

}
