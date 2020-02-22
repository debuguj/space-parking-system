package pl.debuguj.parkingspacessystem.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.debuguj.parkingspacessystem.config.DriverTypeConverter;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.service.ParkingSpaceManagementService;
import pl.debuguj.parkingspacessystem.service.enums.DriverType;
import pl.debuguj.parkingspacessystem.service.exceptions.IncorrectDateException;
import pl.debuguj.parkingspacessystem.service.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.service.exceptions.ParkingSpaceNotFoundException;
import pl.debuguj.parkingspacessystem.service.exceptions.VehicleIsAlreadyActiveInSystemException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grzesiek on 07.10.17.
 */
@RestController
@Slf4j
public class ParkingSpaceController {

    public static final String URI_START_METER = "/startMeter";
    public static final String URI_CHECK_VEHICLE = "/checkVehicle";
    public static final String URI_STOP_METER = "/stopMeter";
    public static final String URI_CHECK_INCOME_PER_DAY = "/checkIncomePerDay";

    private final ParkingSpaceManagementService parkingSpaceManagement;
    private static SimpleDateFormat timeDateFormat;
    private static SimpleDateFormat dayDateFormat;

    public ParkingSpaceController(ParkingSpaceManagementService parkingSpaceManagement) {
        this.parkingSpaceManagement = parkingSpaceManagement;

        timeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dayDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    }

    @PostMapping( value = URI_START_METER + "/{registrationNumber:[0-9]{5}}" )
    public HttpEntity<?> startParkingMeter(
            @PathVariable() String registrationNumber,
            @RequestParam() DriverType driverType,
            @RequestParam() String startTime,
            @RequestParam() String stopTime)
                throws VehicleIsAlreadyActiveInSystemException, IncorrectDateException
    {
        Date begin = validateDate(startTime, timeDateFormat);
        Date end = validateDate(stopTime, timeDateFormat);

        if(begin != null && end != null)
        {
            ParkingSpace ps = new ParkingSpace(registrationNumber, driverType, begin, null);
            parkingSpaceManagement.reserveParkingSpace(ps);
            return new ResponseEntity<>(ps , HttpStatus.OK);
        }
        else {
            throw new IncorrectDateException("Incorrect date format");
        }
    }

    @GetMapping(value = URI_CHECK_VEHICLE + "/{registrationNumber:[0-9]{5}}")
    public HttpEntity<?> checkVehicle(
            @PathVariable String registrationNumber,
            @RequestParam String currentDate)
            throws IncorrectDateException {
        Date date = validateDate(currentDate, timeDateFormat);

        if (date != null) {
            boolean b = parkingSpaceManagement.checkVehicle(registrationNumber, date);
            return new ResponseEntity<>(b, HttpStatus.OK);
        } else {
            throw new IncorrectDateException("Incorrect date format");
        }
    }

    @PutMapping(value = URI_STOP_METER + "/{registrationNumber:[0-9]{5}}")
    public HttpEntity<?> stopParkingMeter(
            @PathVariable String registrationNumber,
            @RequestParam String timeStamp)
            throws IncorrectEndDateException, IncorrectDateException, ParkingSpaceNotFoundException
    {
        Date date = validateDate(timeStamp, timeDateFormat);

        if(date != null)
        {
            BigDecimal fee = parkingSpaceManagement.stopParkingMeter(registrationNumber, date);
            return new ResponseEntity<>(fee, HttpStatus.OK);
        }
        else {
            throw new IncorrectDateException("Incorrect date format");
        }
    }

    @GetMapping(value = URI_CHECK_INCOME_PER_DAY)
    public HttpEntity<?> checkIncomePerDay(
            @RequestParam String date)
            throws IncorrectDateException
    {
        Date tempDate = validateDate(date, dayDateFormat);

        if(date != null)
        {
            BigDecimal sum = parkingSpaceManagement.getIncomePerDay(tempDate);
            return new ResponseEntity<>(sum, HttpStatus.OK);
        }
        else {
            throw new IncorrectDateException("Incorrect date format");
        }
    }

    @InitBinder
    public void initBuilder(final WebDataBinder webDataBinder){
        webDataBinder.registerCustomEditor(DriverType.class, new DriverTypeConverter());
    }


    private Date validateDate(String possibleDate, SimpleDateFormat format)
        throws IncorrectDateException {

        Date date;
        try {
            date = format.parse(possibleDate);
//            DateTimeFormatter fmt =
//                    org.joda.time.format.DateTimeFormat.forPattern(format);
//            date = fmt.parseDateTime(possibleDate).toDate();
        }
        catch (Exception e) {
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Simple exception test", e);
            throw new IncorrectDateException("Incorrect date format");
        }
        return date;
    }
}
