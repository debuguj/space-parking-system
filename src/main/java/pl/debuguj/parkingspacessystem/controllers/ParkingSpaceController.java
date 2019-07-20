package pl.debuguj.parkingspacessystem.controllers;


import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.debuguj.parkingspacessystem.config.Constants;
import pl.debuguj.parkingspacessystem.config.DriverTypeConverter;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.enums.DriverType;
import pl.debuguj.parkingspacessystem.exceptions.CarRegisteredInSystemException;
import pl.debuguj.parkingspacessystem.exceptions.IncorrectDateException;
import pl.debuguj.parkingspacessystem.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.exceptions.ParkingSpaceNotFoundException;
import pl.debuguj.parkingspacessystem.services.ParkingSpaceManagementService;

import java.math.BigDecimal;
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
    private final Constants constants;

    public ParkingSpaceController(ParkingSpaceManagementService parkingSpaceManagement, Constants constants) {
        this.parkingSpaceManagement = parkingSpaceManagement;
        this.constants = constants;
    }

    @PostMapping( value = URI_START_METER + "/{registrationNumber:[0-9]{5}}" )
    public HttpEntity<?> startParkingMeter(
            @PathVariable() String registrationNumber,
            @RequestParam() DriverType driverType,
            @RequestParam() String startTime,
            @RequestParam() String stopTime)
                throws IncorrectEndDateException, CarRegisteredInSystemException, IncorrectDateException
    {
        Date begin = validateDate(startTime, constants.getTimeFormat());
        Date end = validateDate(stopTime, constants.getTimeFormat());

        if(begin != null && end != null)
        {
            ParkingSpace ps = new ParkingSpace(registrationNumber, begin, end);
            ps.setDriverType(driverType);

            return new ResponseEntity<>( parkingSpaceManagement.reserveParkingSpace(ps), HttpStatus.OK);
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
        Date date = validateDate(currentDate, constants.getTimeFormat());

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
        Date date = validateDate(timeStamp, constants.getTimeFormat());

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
        Date tempDate = validateDate(date, constants.getDayFormat());

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


    private Date validateDate(String possibleDate, String format)
        throws IncorrectDateException {

        Date date;
        try {
            DateTimeFormatter fmt =
                    org.joda.time.format.DateTimeFormat.forPattern(format);
            date = fmt.parseDateTime(possibleDate).toDate();
        }
        catch (Exception e) {
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Simple exception test", e);
            throw new IncorrectDateException("Incorrect date format");
        }
        return date;
    }
}
