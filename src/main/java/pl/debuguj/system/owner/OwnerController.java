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
import pl.debuguj.system.spot.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by GB on 07.03.2020.
 */
@RestController
@Slf4j
@Validated
@PropertySource("classpath:global.properties")
class OwnerController {

    private final ArchivedSpotRepo archivedSpotRepo;

    public OwnerController(ArchivedSpotRepo archivedSpotRepo) {
        this.archivedSpotRepo = archivedSpotRepo;
    }

    @GetMapping("${uri.owner.income}")
    public HttpEntity<DailyIncome> getIncomePerDay(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        Stream<ArchivedSpot> archivedSpotStream = archivedSpotRepo.getAllByDay(date);

        if (archivedSpotStream.count() > 0) {
            BigDecimal income = archivedSpotStream
                    .map(ArchivedSpot::getFee)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new ResponseEntity<>(new DailyIncome(date, income), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
