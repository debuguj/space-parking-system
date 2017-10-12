package pl.debuguj.parkingspacessystem.calculations;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.domain.Currency;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;

/**
 * Created by grzesiek on 10.10.17.
 */

public final class PaymentManager {
    private static final Logger logger = LoggerFactory.getLogger(PaymentManager.class);
    private static final BigDecimal BEGIN_REGULAR_FEE = BigDecimal.ONE;
    private static final BigDecimal FACTOR_REGULAR_FEE = new BigDecimal("2.0");
    private static final BigDecimal BEGIN_VIP_FEE = BigDecimal.ZERO;
    private static final BigDecimal FACTOR_VIP_FEE = new BigDecimal("1.5");


    private static Currency currency;

    static {
        currency = Currency.PL;
    }

    public void setCurrency(Currency c) {
        this.currency = c;
    }


    public static BigDecimal getFee(ParkingSpace ps)
    {

        BigDecimal fee = BigDecimal.ZERO;

        switch(ps.getDriverType()){
            case REGULAR:
                fee = getBasicFee(getPeriod(ps), BEGIN_REGULAR_FEE, FACTOR_REGULAR_FEE);
                break;
            case VIP:
                fee = getBasicFee(getPeriod(ps), BEGIN_VIP_FEE, FACTOR_VIP_FEE);
                break;
            default:
                break;
        }

        return fee.multiply(currency.getExchangeRate());
    }

    private static BigDecimal getBasicFee(BigDecimal period, BigDecimal startSum, BigDecimal factor)
    {
        int compResult = period.compareTo(BigDecimal.ONE);

        if(compResult == 0) {
            return startSum;
        }
        else if (compResult == 1)
        {
            BigDecimal current = BigDecimal.ONE;

            for(int i=1; i<period.intValueExact(); i++)
            {
                current = current.multiply(factor);
                startSum = startSum.add(current);
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
