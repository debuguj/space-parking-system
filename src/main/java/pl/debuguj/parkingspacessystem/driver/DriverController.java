package pl.debuguj.parkingspacessystem.driver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.debuguj.parkingspacessystem.spot.Currency;
import pl.debuguj.parkingspacessystem.spot.DriverType;
import pl.debuguj.parkingspacessystem.spot.SpaceManagementService;
import pl.debuguj.parkingspacessystem.spot.Spot;
import pl.debuguj.parkingspacessystem.spot.exceptions.IncorrectEndDateException;
import pl.debuguj.parkingspacessystem.spot.exceptions.ParkingSpaceNotFoundException;
import pl.debuguj.parkingspacessystem.spot.exceptions.VehicleIsAlreadyActiveInSystemException;
import pl.debuguj.parkingspacessystem.spot.validation.DriverTypeSubSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by GB on 07.03.20.
 */
@RestController
@Slf4j
@Validated
@PropertySource("classpath:global.properties")
public class DriverController {

    private final SpaceManagementService spaceManagement;

    public DriverController(SpaceManagementService spaceManagement) {
        this.spaceManagement = spaceManagement;
    }

    @PostMapping("${uri.start.meter}")
    public HttpEntity<Spot> startParkingMeter(
            @PathVariable String registrationNumber,
            @RequestParam DriverType driverType,
            @RequestParam Date startTime) {

        Spot sa = new Spot(registrationNumber, driverType, startTime);
        try {
            if (spaceManagement.reserveParkingSpace(sa))
                return new ResponseEntity<>(sa, HttpStatus.OK);
            return new ResponseEntity<>(sa, HttpStatus.NOT_ACCEPTABLE);
        } catch (VehicleIsAlreadyActiveInSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle is active", e);
        }
    }

    @PutMapping("${uri.stop.meter}")
    public HttpEntity<BigDecimal> stopParkingMeter(
            @PathVariable String registrationNumber,
            @RequestParam Date timeStamp) {
        try {
            //TODO remove constant currency
            BigDecimal fee = spaceManagement.stopParkingMeter(registrationNumber, timeStamp, Currency.PLN);
            return new ResponseEntity<>(fee, HttpStatus.OK);
        } catch (IncorrectEndDateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect date format", e);
        } catch (ParkingSpaceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking space not exists in database", e);
        }
    }
}
