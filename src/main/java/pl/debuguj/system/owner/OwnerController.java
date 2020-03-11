package pl.debuguj.system.owner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.debuguj.system.spot.Currency;
import pl.debuguj.system.spot.Spot;
import pl.debuguj.system.spot.SpotRepo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by GB on 07.03.2020.
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

    @GetMapping("${uri.owner.income}")
    public HttpEntity<DailyIncome> getIncomePerDay(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        Stream<Spot> spotStream = spotRepo.findAllFinished(date);

        if (spotStream.count() > 0) {
            BigDecimal income = spotRepo.findAllFinished(date)
                    .map(s -> s.getFee(Currency.PLN).get())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            DailyIncome dailyIncome = new DailyIncome(date, income);
            return new ResponseEntity<>(dailyIncome, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        //TODO remove constant currency
    }
}
