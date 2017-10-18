package pl.debuguj.parkingspacessystem.controllers;


import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import pl.debuguj.parkingspacessystem.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.exceptions.ParkingSpaceNotFoundException;
import pl.debuguj.parkingspacessystem.services.ParkingSpaceManagementService;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by grzesiek on 07.10.17.
 */
@RestController
public class ParkingSpaceController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingSpaceController.class);

    public static final String URI_START_METER = "/startMeter";
    public static final String URI_CHECK_VEHICLE = "/checkVehicle";
    public static final String URI_STOP_METER = "/stopMeter";
    public static final String URI_CHECK_INCOME_PER_DAY = "/checkIncomePerDay";

    private final ParkingSpaceManagementService parkingSpaceManagement;

    @Autowired
    private Constants constants;


    public ParkingSpaceController(ParkingSpaceManagementService parkingSpaceManagement) {
        this.parkingSpaceManagement = parkingSpaceManagement;

    }

    @PostMapping( value = URI_START_METER + "/{registrationNumber:[0-9]{5}}" )
    public HttpEntity<BigDecimal> startParkingMeter(
            @PathVariable() final String registrationNumber,
            @RequestParam() final DriverType driverType,
            @RequestParam() final String startTime,
            @RequestParam() final String stopTime)  {

        try {
            Date begin = validateDate(startTime, constants.getTimeFormat());
            Date end = validateDate(stopTime, constants.getTimeFormat());

            if(begin != null && end != null)
            {
                ParkingSpace ps = new ParkingSpace(registrationNumber, begin, end);
                ps.setDriverType(driverType);

                return new HttpEntity<>( parkingSpaceManagement.reserveParkingSpace(ps));
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (IncorrectEndDateException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (CarRegisteredInSystemException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = URI_CHECK_VEHICLE + "/{registrationNumber:[0-9]{5}}")
    public HttpEntity<Boolean> checkVehicle(
            @PathVariable final String registrationNumber,
            @RequestParam final String currentDate)
    {
        try {
            Date date = validateDate(currentDate, constants.getTimeFormat());
            if(date != null)
            {
                return new HttpEntity(parkingSpaceManagement.checkVehicle(registrationNumber, date));
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = URI_STOP_METER + "/{registrationNumber:[0-9]{5}}")
    public HttpEntity<BigDecimal> stopParkingMeter(
            @PathVariable final String registrationNumber,
            @RequestParam final String timeStamp)
    {
        try {
            Date date = validateDate(timeStamp, constants.getTimeFormat());
            if(date != null)
            {
                BigDecimal fee = parkingSpaceManagement.stopParkingMeter(registrationNumber, date);
                return new HttpEntity(fee);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (IncorrectEndDateException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (ParkingSpaceNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = URI_CHECK_INCOME_PER_DAY)
    public HttpEntity<BigDecimal> checkIncomePerDay(
            @RequestParam final String date)
    {
        try {
            Date tempDate = validateDate(date, constants.getDayFormat());

            if(date != null) {

                BigDecimal sum = parkingSpaceManagement.getIncomePerDay(tempDate);
                return new HttpEntity(sum);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @InitBinder
    public void initBuilder(final WebDataBinder webDataBinder){
        webDataBinder.registerCustomEditor(DriverType.class, new DriverTypeConverter());
    }


    private Date validateDate(String possibleDate, String format){

        DateTimeFormatter fmt =
            org.joda.time.format.DateTimeFormat.forPattern(format);

        return fmt.parseDateTime(possibleDate).toDate();

    }
}
