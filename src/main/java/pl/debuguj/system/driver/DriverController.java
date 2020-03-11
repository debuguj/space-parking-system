package pl.debuguj.system.driver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.debuguj.system.spot.Spot;
import pl.debuguj.system.spot.SpotRepo;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public HttpEntity<Spot> stopParkingMeter(
            @PathVariable @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$") String plate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date finishDate) {

        Optional<Spot> newOs = spotRepo.updateFinishDateByPlate(plate, finishDate);
        if (newOs.isPresent()) {
            return new ResponseEntity<>(newOs.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private Optional<Date> getDate(final String stringDate) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final Date date;
        try {
            return Optional.ofNullable(sdf.parse(stringDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @PostMapping(value = "${uri.simple}")
    public HttpEntity<?> simpleReturn(@PathVariable("plate") @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$") String plate) {

        return new ResponseEntity<>(BigDecimal.TEN, HttpStatus.OK);
    }

}
