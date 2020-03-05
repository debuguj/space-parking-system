package pl.debuguj.parkingspacessystem.application;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.debuguj.parkingspacessystem.spaces.SpaceActive;
import pl.debuguj.parkingspacessystem.spaces.SpaceManagementService;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;
import pl.debuguj.parkingspacessystem.spaces.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.spaces.exceptions.ParkingSpaceNotFoundException;
import pl.debuguj.parkingspacessystem.spaces.exceptions.VehicleIsAlreadyActiveInSystemException;
import pl.debuguj.parkingspacessystem.spaces.validation.DriverTypeSubSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by GB on 07.10.17.
 */
@RestController
@Slf4j
@Validated
@PropertySource("classpath:global.properties")
class SpaceController {

    private final SpaceManagementService spaceManagement;

    public SpaceController(SpaceManagementService spaceManagement) {
        this.spaceManagement = spaceManagement;
    }

    @PostMapping("${uri.start.meter}")
    public HttpEntity<SpaceActive> startParkingMeter(
            @PathVariable() @NotNull @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$")
                    String registrationNumber,
            @RequestParam() @NotNull @DriverTypeSubSet(anyOf = {DriverType.REGULAR, DriverType.VIP})
                    DriverType driverType,
            @RequestParam() @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    Date startTime) {

        SpaceActive sa = new SpaceActive(registrationNumber, driverType, startTime);
        try {
            if (spaceManagement.reserveParkingSpace(sa))
                return new ResponseEntity<>(sa, HttpStatus.OK);
            return new ResponseEntity<>(sa, HttpStatus.NOT_ACCEPTABLE);
        } catch (VehicleIsAlreadyActiveInSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle is active", e);
        }
    }

    @GetMapping("${uri.check.vehicle}")
    public HttpEntity checkVehicle(
            @PathVariable @NotNull @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$")
                    String registrationNumber,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    Date currentDate) {

        boolean b = spaceManagement.checkVehicle(registrationNumber, currentDate);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @PutMapping("${uri.stop.meter}")
    public HttpEntity<BigDecimal> stopParkingMeter(
            @PathVariable @NotNull @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$")
                    String registrationNumber,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    Date timeStamp) {
        try {
            BigDecimal fee = spaceManagement.stopParkingMeter(registrationNumber, timeStamp);
            return new ResponseEntity<>(fee, HttpStatus.OK);
        } catch (IncorrectEndDateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect date format", e);
        } catch (ParkingSpaceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking space not exists in database", e);
        }
    }

    @GetMapping("${uri.check.income.per.day}")
    public HttpEntity<BigDecimal> checkIncomePerDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    Date date) {

        BigDecimal sum = spaceManagement.getIncomePerDay(date);
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }
}
