package pl.debuguj.parkingspacessystem.calculation;

/**
 * Created by grzesiek on 07.10.17.
 */

import org.springframework.stereotype.Service;
import pl.debuguj.parkingspacessystem.domain.DriverType;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;

@Service
public class PaymentManager {

    private Currency currency;

    public PaymentManager() {
    }

    public void setCurrency(Currency c) {
        this.currency = c;
    }

//    TODO implement correct estimation
    private BigDecimal getReguralFee(ParkingSpace ps) {
        return BigDecimal.TEN;
//        BigDecimal startSum = beginSum;
//        if(period == 1) {
//            return startSum;
//        }
//        else if (period > 1)
//        {
//            BigDecimal current = BigDecimal.ONE;
//
//            for(int i=1; i<period; i++)
//            {
//                current = current.multiply(factor);
//                startSum.add(current);
//            }
//            return startSum;
//        } else {
//            return BigDecimal.ZERO;
//        }
    }
    //    TODO implement correct estimation
    private BigDecimal getVipFee(ParkingSpace ps) {
        return BigDecimal.TEN;
//        BigDecimal startSum = beginSum;
//        if(period == 1) {
//            return startSum;
//        }
//        else if(period == 2){
//            return null;
//        }
//        else if (period > 2)
//        {
//            BigDecimal current = BigDecimal.ZERO;
//
//            for(int i=1; i<period; i++)
//            {
//                current = current.multiply(factor);
//                startSum.add(current);
//            }
//            return startSum;
//        } else {
//            return BigDecimal.ZERO;
//        }
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
}
