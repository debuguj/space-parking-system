package pl.debuguj.parkingspacessystem.driver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.debuguj.parkingspacessystem.spot.Currency;
import pl.debuguj.parkingspacessystem.spot.Spot;
import pl.debuguj.parkingspacessystem.spot.SpotRepo;

import javax.validation.Valid;
import java.math.BigDecimal;
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

    public DriverController(SpotRepo spotRepo) {
        this.spotRepo = spotRepo;
    }

    @PostMapping("${uri.start.meter}")
    public HttpEntity<?> startParkingMeter(@RequestBody @Valid Spot spot) {

        Optional<Boolean> os = spotRepo.save(spot);
        if (os.isPresent() && os.get()) {
            return new ResponseEntity<>(spot, HttpStatus.ACCEPTED);
        } else if (os.isPresent() && !os.get()) {
            return new ResponseEntity<>(spot, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(spot, HttpStatus.BAD_REQUEST);
        }
    }

    //TODO: find better way to return value
    @PatchMapping("${uri.stop.meter}")
    public HttpEntity<?> stopParkingMeter(
            @RequestBody @Valid Spot spot) {

        Optional<Spot> os = spotRepo.updateFinishDate(spot.getVehicleRegistrationNumber(), spot.getFinishDate());
        if (os.isPresent()) {
            return new ResponseEntity<>(os.get().getFee(Currency.PLN), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(BigDecimal.ZERO, HttpStatus.NOT_FOUND);
        }
    }
}
