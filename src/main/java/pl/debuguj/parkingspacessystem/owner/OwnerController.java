package pl.debuguj.parkingspacessystem.owner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.debuguj.parkingspacessystem.spot.Currency;
import pl.debuguj.parkingspacessystem.spot.SpaceManagementService;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by GB on 07.03.20.
 */
@RestController
@Slf4j
@Validated
@PropertySource("classpath:global.properties")
public class OwnerController {
    private final SpaceManagementService spaceManagement;

    public OwnerController(SpaceManagementService spaceManagement) {
        this.spaceManagement = spaceManagement;
    }

    @GetMapping("${uri.check.income.per.day}")
    public HttpEntity<BigDecimal> checkIncomePerDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    Date date) {

        //TODO remove constant currency
        BigDecimal sum = spaceManagement.getIncomePerDay(date, Currency.PLN);
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }
}
