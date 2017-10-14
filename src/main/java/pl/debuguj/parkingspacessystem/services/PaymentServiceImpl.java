package pl.debuguj.parkingspacessystem.services;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.enums.Currency;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;

/**
 * Created by grzesiek on 10.10.17.
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

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
            return fee.multiply(currency.getExchangeRate()).setScale(1);
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
        DateTime dt1 = new DateTime(ps.getBeginTime());
        DateTime dt2 = new DateTime(ps.getEndTime());

        BigDecimal minutes = new BigDecimal(Minutes.minutesBetween(dt1, dt2).getMinutes());
        BigDecimal div = new BigDecimal(60);

        return minutes.divide(div, BigDecimal.ROUND_CEILING);
    }
}
