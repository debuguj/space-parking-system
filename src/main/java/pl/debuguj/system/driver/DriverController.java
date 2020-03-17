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
import java.util.Optional;

/**
 * Created by GB on 07.03.20.
 */
@RestController
@Slf4j
@Validated
@PropertySource("classpath:global.properties")
public class DriverController {

    private final SpotRepo spotRepo;
    private final ArchivedSpotRepo archivedSpotRepo;

    public DriverController(SpotRepo spotRepo, ArchivedSpotRepo archivedSpotRepo) {
        this.spotRepo = spotRepo;
        this.archivedSpotRepo = archivedSpotRepo;
    }

    @PostMapping(value = "${uri.driver.start}")
    public HttpEntity<Spot> startParkingMeter(@RequestBody @Valid Spot spot) {
        Optional<Spot> os = spotRepo.save(spot);
        if (os.isPresent()) {
            return new ResponseEntity<>(spot, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(spot, HttpStatus.FOUND);
        }
    }

    @PatchMapping("${uri.driver.stop}")
    public HttpEntity<Fee> stopParkingMeter(
            @PathVariable @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$") String vehiclePlate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date finishDate) {

        final Optional<Spot> oSpot = spotRepo.findByPlate(vehiclePlate);

        if (oSpot.isPresent()) {
            final Spot spot = oSpot.get();
            final ArchivedSpot archivedSpot = new ArchivedSpot(spot.getVehiclePlate(), spot.getDriverType(), spot.getBeginDate(), finishDate);
            archivedSpotRepo.save(archivedSpot);
            spotRepo.delete(vehiclePlate);
            Fee fee = new Fee(archivedSpot.getVehiclePlate(), archivedSpot.getBeginDate(), archivedSpot.getFinishDate(), archivedSpot.getFee().get());
            return new ResponseEntity(fee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "${uri.simple}")
    public HttpEntity<?> simpleReturn(@PathVariable("plate") @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$") String plate) {

        return new ResponseEntity<>(BigDecimal.TEN, HttpStatus.OK);
    }

}
