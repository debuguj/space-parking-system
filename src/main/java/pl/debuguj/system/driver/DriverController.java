package pl.debuguj.system.driver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.debuguj.system.spot.*;

import javax.validation.Valid;
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
class DriverController {

    private final SpotRepo spotRepo;
    private final ArchivedSpotRepo archivedSpotRepo;

    public DriverController(SpotRepo spotRepo, ArchivedSpotRepo archivedSpotRepo) {
        this.spotRepo = spotRepo;
        this.archivedSpotRepo = archivedSpotRepo;
    }

    @PostMapping(value = "${uri.driver.start}")
    public HttpEntity<Spot> startParkingMeter(@RequestBody @Valid Spot spot) {

        spotRepo.findByVehiclePlate(spot.getVehiclePlate()).orElseThrow(() -> new VehicleActiveInDbException(spot.getVehiclePlate()));

        final Spot registeredSpot = spotRepo.save(spot).orElseThrow(() -> new VehicleCannotBeRegisteredInDbException(spot.getVehiclePlate()));

        return new ResponseEntity<>(registeredSpot, HttpStatus.OK);
    }

    @PatchMapping("${uri.driver.stop}")
    public HttpEntity<Fee> stopParkingMeter(
            @PathVariable @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$") String vehiclePlate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date finishDate) {

        final Spot spot = spotRepo.findByVehiclePlate(vehiclePlate).orElseThrow(() -> new VehicleNotExistsInDbException(vehiclePlate));

        final ArchivedSpot archivedSpot = new ArchivedSpot(spot.getVehiclePlate(), spot.getDriverType(), spot.getBeginDate(), finishDate);

        archivedSpotRepo.save(archivedSpot);
        spotRepo.delete(vehiclePlate);

        return new ResponseEntity(new Fee(archivedSpot), HttpStatus.OK);
    }

    @PostMapping(value = "${uri.simple}")
    public HttpEntity<?> simpleReturn(@PathVariable("plate") @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$") String plate) {

        return new ResponseEntity<>(BigDecimal.TEN, HttpStatus.OK);
    }
}
