package pl.debuguj.system.driver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.debuguj.system.spot.Currency;
import pl.debuguj.system.spot.Spot;
import pl.debuguj.system.spot.SpotRepo;

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

    public DriverController(SpotRepo spotRepo) {
        this.spotRepo = spotRepo;
    }

    @PostMapping("${uri.driver.start}")
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
    @PatchMapping("${uri.driver.stop}")
    public HttpEntity<?> stopParkingMeter(
            @PathVariable @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$") String plate) {

        Optional<Spot> os = spotRepo.updateFinishDate(plate, new Date());
        if (os.isPresent()) {
            return new ResponseEntity<>(os.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
