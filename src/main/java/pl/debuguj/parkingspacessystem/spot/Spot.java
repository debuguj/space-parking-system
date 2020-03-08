package pl.debuguj.parkingspacessystem.spot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.debuguj.parkingspacessystem.spot.validation.DriverTypeSubSet;
import pl.debuguj.parkingspacessystem.spot.validation.FarePeriod;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@FarePeriod
@AllArgsConstructor
@Getter
public class Spot implements Serializable {

    private final UUID uuid = UUID.randomUUID();
    @NotNull
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$")
    private final String vehicleRegistrationNumber;
    @NotNull
    @DriverTypeSubSet(anyOf = {DriverType.REGULAR, DriverType.VIP})
    private final DriverType driverType;
    @NotNull
    private final Date beginDate;
    private Date finishDate;

    public Spot(final String vehicleRegistrationNumber, final DriverType driverType, final Date beginDate) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.driverType = driverType;
        this.beginDate = beginDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spot that = (Spot) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public Optional<BigDecimal> getFee(final Currency currency) {
        if (Objects.nonNull(getFinishDate())) {
            BigDecimal fee = getBasicFee();
            return Optional.ofNullable(fee.multiply(currency.getExchangeRate()).setScale(1, BigDecimal.ROUND_CEILING));
        } else {
            return Optional.empty();
        }

    }

    private BigDecimal getBasicFee() {
        final BigDecimal period = getPeriod();
        BigDecimal startSum = this.getDriverType().getBeginValue();
        final BigDecimal factor = this.getDriverType().getFactor();

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
     * @return Period of parking time in hours
     */
    private BigDecimal getPeriod() {
        LocalDateTime from = getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime to = getFinishDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        BigDecimal minutes = new BigDecimal(from.until(to, ChronoUnit.MINUTES));
        BigDecimal div = new BigDecimal(60);

        return minutes.divide(div, BigDecimal.ROUND_CEILING);
    }
}
