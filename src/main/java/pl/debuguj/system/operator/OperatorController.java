package pl.debuguj.system.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.debuguj.system.spot.Spot;
import pl.debuguj.system.spot.SpotRepo;

import javax.validation.constraints.Pattern;

@RestController
@Slf4j
@Validated
@PropertySource("classpath:global.properties")
class OperatorController {

    private final SpotRepo spotRepo;

    public OperatorController(SpotRepo spotRepo) {
        this.spotRepo = spotRepo;
    }

    @GetMapping("${uri.operator.check}")
    public HttpEntity<Spot> checkVehicleByPlate(@PathVariable @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$") String plate) {

        final Spot spot = spotRepo.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));

        return new ResponseEntity<>(spot, HttpStatus.OK);
    }
}
