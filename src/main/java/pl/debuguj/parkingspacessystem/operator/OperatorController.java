package pl.debuguj.parkingspacessystem.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.debuguj.parkingspacessystem.spot.SpaceManagementService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@RestController
@Slf4j
@Validated
@PropertySource("classpath:global.properties")
public class OperatorController {

    private final SpaceManagementService spaceManagement;

    public OperatorController(SpaceManagementService spaceManagement) {
        this.spaceManagement = spaceManagement;
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
}
