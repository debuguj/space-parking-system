package pl.debuguj.parkingspacessystem.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.debuguj.parkingspacessystem.spot.Spot;
import pl.debuguj.parkingspacessystem.spot.SpotRepo;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Slf4j
@Validated
@PropertySource("classpath:global.properties")
public class OperatorController {

    private final SpotRepo spotRepo;

    public OperatorController(SpotRepo spotRepo) {
        this.spotRepo = spotRepo;
    }

    @GetMapping("${uri.check.vehicle}")
    public HttpEntity checkVehicle(@RequestBody @Valid Spot spot) {

        Optional<Spot> os = spotRepo.findActive(spot.getVehicleRegistrationNumber());
        if (os.isPresent()) {
            return new ResponseEntity<>(os.get(), HttpStatus.FOUND);
        } else {
            //TODO: remove null
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
