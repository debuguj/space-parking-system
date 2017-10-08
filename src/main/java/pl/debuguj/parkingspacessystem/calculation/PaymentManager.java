package pl.debuguj.parkingspacessystem.calculation;

/**
 * Created by grzesiek on 07.10.17.
 */

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.domain.DriverType;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class PaymentManager {

    //private static final Logger logger = LoggerFactory.getLogger(PaymentManager.class);

    private Currency currency;

    public PaymentManager() {
    }

    public void setCurrency(Currency c) {
        this.currency = c;
    }

    public BigDecimal getFee(ParkingSpace ps)
    {
        BigDecimal fee = BigDecimal.ZERO;

        if(DriverType.REGULAR.equals(ps.getDriverType()))
        {
            fee = getReguralFee(ps);
        }
        else if (DriverType.VIP.equals(ps.getDriverType()))
        {
            fee = getVipFee(ps);
        }
        return fee.multiply(currency.getExchangeRate());
    }

    private BigDecimal getReguralFee(ParkingSpace ps)
    {
        BigDecimal period = getPeriod(ps.getBeginTime(), ps.getEndTime());
        BigDecimal startSum = BigDecimal.ONE;
        BigDecimal factor = new BigDecimal("2.0");

        int compResult = period.compareTo(BigDecimal.ONE);
        return getBasicFee(compResult, period, startSum, factor);
    }

    private BigDecimal getVipFee(ParkingSpace ps)
    {
        BigDecimal period = getPeriod(ps.getBeginTime(), ps.getEndTime());
        BigDecimal startSum = BigDecimal.ZERO;
        BigDecimal factor = new BigDecimal("1.5");

        int compResult = period.compareTo(BigDecimal.ONE);

        return getBasicFee(compResult, period, startSum, factor);
    }

    private BigDecimal getBasicFee(int compResult, BigDecimal period,
                                   BigDecimal startSum, BigDecimal factor)
    {
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
     * @param d1 Start parking time
     * @param d2 Stop parking time
     * @return Period od parking time in hours
     */
    private BigDecimal getPeriod(Date d1, Date d2)
    {
        DateTime dt1 = new DateTime(d1);
        DateTime dt2 = new DateTime(d2);

        BigDecimal minutes = new BigDecimal(Minutes.minutesBetween(dt1, dt2).getMinutes());
        BigDecimal div = new BigDecimal(60);

        return minutes.divide(div, BigDecimal.ROUND_CEILING);
    }
}
