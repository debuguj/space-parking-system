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
import pl.debuguj.parkingspacessystem.spot.Spot;
import pl.debuguj.parkingspacessystem.spot.SpotRepo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by GB on 07.03.20.
 */
@RestController
@Slf4j
@Validated
@PropertySource("classpath:global.properties")
public class OwnerController {

    private final SpotRepo spotRepo;

    public OwnerController(SpotRepo spotRepo) {
        this.spotRepo = spotRepo;
    }

    @GetMapping("${uri.check.income.per.day}")
    public HttpEntity<BigDecimal> checkIncomePerDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    Date date) {

        Stream<Spot> spotStream = spotRepo.findAllFinished(date);
        if (spotStream.count() > 0) {
            BigDecimal income = spotRepo.findAllFinished(date)
                    .map(s -> s.getFee(Currency.PLN).get())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return new ResponseEntity<>(income, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(BigDecimal.ZERO, HttpStatus.NOT_FOUND);
        }
        //TODO remove constant currency
    }
}
