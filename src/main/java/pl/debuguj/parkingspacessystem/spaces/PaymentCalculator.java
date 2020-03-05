package pl.debuguj.parkingspacessystem.spaces;

import lombok.extern.slf4j.Slf4j;
import pl.debuguj.parkingspacessystem.spaces.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@Slf4j
class PaymentCalculator {

    private Currency currency;

    public PaymentCalculator() {
        currency = Currency.PLN;
    }

    public void setCurrency(final Currency c) {
        this.currency = c;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Optional<BigDecimal> getFee(final SpaceFinished spaceFinished) {

        if (Objects.nonNull(spaceFinished)) {
            BigDecimal fee = getBasicFee(spaceFinished);

            return Optional.ofNullable(fee.multiply(currency.getExchangeRate()).setScale(1, BigDecimal.ROUND_CEILING));
        }
        return Optional.empty();
    }

    private BigDecimal getBasicFee(final SpaceFinished spaceFinished) {
        final BigDecimal period = getPeriod(spaceFinished);
        BigDecimal startSum = spaceFinished.getDriverType().getBeginValue();
        final BigDecimal factor = spaceFinished.getDriverType().getFactor();

        int compResult = period.compareTo(BigDecimal.ONE);

        if (compResult == 0) {
            return startSum;
        } else if (compResult == 1) {
            BigDecimal current = new BigDecimal("2.0");

            for (int i = 1; i < period.intValueExact(); i++) {
                startSum = startSum.add(current);
                current = current.multiply(factor);
            }
            return startSum;
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Return period rounds to ceil (hours)
     *
     * @param ps Parking space object
     * @return Period of parking time in hours
     */
    private BigDecimal getPeriod(SpaceFinished ps) {
        LocalDateTime from = ps.getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime to = ps.getFinishDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        BigDecimal minutes = new BigDecimal(from.until(to, ChronoUnit.MINUTES));
        BigDecimal div = new BigDecimal(60);

        return minutes.divide(div, BigDecimal.ROUND_CEILING);
    }
}
