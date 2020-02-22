package pl.debuguj.parkingspacessystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.service.PaymentService;
import pl.debuguj.parkingspacessystem.service.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Created by grzesiek on 10.10.17.
 */
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private Currency currency;

    public PaymentServiceImpl() {
        currency = Currency.PLN;
    }

    @Override
    public void setCurrency(final Currency c) {
        this.currency = c;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public BigDecimal getFee(final ParkingSpace parkingSpace)
    {
        if(null != parkingSpace) {
            BigDecimal fee = getBasicFee(parkingSpace);

            return fee.multiply(currency.getExchangeRate()).setScale(1, BigDecimal.ROUND_CEILING);
        }
        return null;
    }

    private static BigDecimal getBasicFee(ParkingSpace parkingSpace)
    {
        BigDecimal period = getPeriod(parkingSpace);
        BigDecimal startSum = parkingSpace.getDriverType().getBeginValue();
        BigDecimal factor = parkingSpace.getDriverType().getFactor();

        int compResult = period.compareTo(BigDecimal.ONE);

        if(compResult == 0) {
            return startSum;
        }
        else if (compResult == 1)
        {
            BigDecimal current = new BigDecimal("2.0");

            for(int i=1; i<period.intValueExact(); i++)
            {
                startSum = startSum.add(current);
                current = current.multiply(factor);
            }
            return startSum;
        } else {
            return BigDecimal.ZERO;
        }
    }
    /**
     * Return period round to ceil (hours)
     * @param ps Parking space object
     * @return Period of parking time in hours
     */
    private static BigDecimal getPeriod(ParkingSpace ps)
    {
        LocalDateTime from = ps.getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime to= ps.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        //BigDecimal minutes = new BigDecimal(Minutes.minutesBetween(dt1, dt2).getMinutes());
        BigDecimal minutes = new BigDecimal(from.until(to, ChronoUnit.MINUTES));
        BigDecimal div = new BigDecimal(60);

        return minutes.divide(div, BigDecimal.ROUND_CEILING);
    }
}
