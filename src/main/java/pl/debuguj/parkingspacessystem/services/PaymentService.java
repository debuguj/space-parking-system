package pl.debuguj.parkingspacessystem.services;

import pl.debuguj.parkingspacessystem.domain.Currency;
import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;

/**
 * Created by grzesiek on 09.10.17.
 */
public interface PaymentService {

    public void setCurrency(Currency currency);
    public BigDecimal getFee(ParkingSpace ps);
}
