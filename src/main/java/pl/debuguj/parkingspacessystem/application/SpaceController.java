package pl.debuguj.parkingspacessystem.application;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.debuguj.parkingspacessystem.spaces.SpaceActive;
import pl.debuguj.parkingspacessystem.spaces.SpaceManagementService;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;
import pl.debuguj.parkingspacessystem.spaces.exceptions.IncorrectDateException;
import pl.debuguj.parkingspacessystem.spaces.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.spaces.exceptions.ParkingSpaceNotFoundException;
import pl.debuguj.parkingspacessystem.spaces.exceptions.VehicleIsAlreadyActiveInSystemException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grzesiek on 07.10.17.
 */
@RestController
@Slf4j
class SpaceController {

    public static final String URI_START_METER = "/startMeter";
    public static final String URI_CHECK_VEHICLE = "/checkVehicle";
    public static final String URI_STOP_METER = "/stopMeter";
    public static final String URI_CHECK_INCOME_PER_DAY = "/checkIncomePerDay";

    private final SpaceManagementService spaceManagement;
    private static SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public SpaceController(SpaceManagementService spaceManagement) {
        this.spaceManagement = spaceManagement;
    }

    @PostMapping(value = URI_START_METER + "/{registrationNumber:[0-9]{5}}")
    public HttpEntity<?> startParkingMeter(
            @PathVariable() String registrationNumber,
            @RequestParam() DriverType driverType,
            @RequestParam() String startTime,
            @RequestParam() String stopTime)
            throws VehicleIsAlreadyActiveInSystemException, IncorrectDateException {
        Date begin = validateDate(startTime, timeDateFormat);
        Date end = validateDate(stopTime, timeDateFormat);

        if (begin != null && end != null) {
            SpaceActive ps = new SpaceActive(registrationNumber, driverType, begin);
            spaceManagement.reserveParkingSpace(ps);
            return new ResponseEntity<>(ps, HttpStatus.OK);
        } else {
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
            boolean b = spaceManagement.checkVehicle(registrationNumber, date);
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
            BigDecimal fee = spaceManagement.stopParkingMeter(registrationNumber, date);
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
            BigDecimal sum = spaceManagement.getIncomePerDay(tempDate);
            return new ResponseEntity<>(sum, HttpStatus.OK);
        }
        else {
            throw new IncorrectDateException("Incorrect date format");
        }
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
