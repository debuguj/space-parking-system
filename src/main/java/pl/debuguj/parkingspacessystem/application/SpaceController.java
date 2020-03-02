package pl.debuguj.parkingspacessystem.application;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.debuguj.parkingspacessystem.spaces.SpaceActive;
import pl.debuguj.parkingspacessystem.spaces.SpaceManagementService;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;
import pl.debuguj.parkingspacessystem.spaces.exceptions.IncorrectDateException;
import pl.debuguj.parkingspacessystem.spaces.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.spaces.exceptions.ParkingSpaceNotFoundException;
import pl.debuguj.parkingspacessystem.spaces.exceptions.VehicleIsAlreadyActiveInSystemException;
import pl.debuguj.parkingspacessystem.spaces.validation.DateTimeFormat;
import pl.debuguj.parkingspacessystem.spaces.validation.DriverTypeSubSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GB on 07.10.17.
 */
@RestController
@Slf4j
@Validated
class SpaceController {

    public static final String URI_START_METER = "/startMeter";
    public static final String URI_CHECK_VEHICLE = "/checkVehicle";
    public static final String URI_STOP_METER = "/stopMeter";
    public static final String URI_CHECK_INCOME_PER_DAY = "/checkIncomePerDay";

    private static SimpleDateFormat timeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final SpaceManagementService spaceManagement;

    public SpaceController(SpaceManagementService spaceManagement) {
        this.spaceManagement = spaceManagement;
    }

    @PostMapping(value = URI_START_METER + "/")
    public HttpEntity startParkingMeter(
            @PathVariable() @NotNull @NotEmpty @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$")
                    String registrationNumber,
            @RequestParam() @NotNull @DriverTypeSubSet(anyOf = {DriverType.REGULAR, DriverType.VIP})
                    DriverType driverType,
            @RequestParam() @NotNull @NotEmpty @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                    String startTime)
            throws VehicleIsAlreadyActiveInSystemException, ParseException {

        SpaceActive ps = new SpaceActive(registrationNumber, driverType, timeDateFormat.parse(startTime));
        spaceManagement.reserveParkingSpace(ps);
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }

    @GetMapping(value = URI_CHECK_VEHICLE + "/")
    public HttpEntity checkVehicle(
            @PathVariable @NotNull @NotEmpty @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$")
                    String registrationNumber,
            @RequestParam @NotNull @NotEmpty @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                    String currentDate)
            throws ParseException {

        boolean b = spaceManagement.checkVehicle(registrationNumber, timeDateFormat.parse(currentDate));
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @PutMapping(value = URI_STOP_METER + "/")
    public HttpEntity stopParkingMeter(
            @PathVariable @NotNull @NotEmpty @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$")
                    String registrationNumber,
            @RequestParam @NotNull @NotEmpty @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                    String timeStamp)
            throws IncorrectEndDateException, ParseException, ParkingSpaceNotFoundException {

        BigDecimal fee = spaceManagement.stopParkingMeter(registrationNumber, timeDateFormat.parse(timeStamp));
        return new ResponseEntity<>(fee, HttpStatus.OK);
    }

    @GetMapping(value = URI_CHECK_INCOME_PER_DAY)
    public HttpEntity checkIncomePerDay(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")
                    String date)
            throws ParseException {

        BigDecimal sum = spaceManagement.getIncomePerDay(dayDateFormat.parse(date));
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }

}
