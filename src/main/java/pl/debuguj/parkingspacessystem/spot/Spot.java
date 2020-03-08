package pl.debuguj.parkingspacessystem.spot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.debuguj.parkingspacessystem.spot.validation.DateTimeFormat;
import pl.debuguj.parkingspacessystem.spot.validation.DriverTypeSubSet;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final Date beginDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
        BigDecimal fee = getBasicFee();
        return Optional.ofNullable(fee.multiply(currency.getExchangeRate()).setScale(1, BigDecimal.ROUND_CEILING));
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
