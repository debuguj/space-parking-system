package pl.debuguj.parkingspacessystem.services;

import pl.debuguj.parkingspacessystem.domain.ParkingSpace;
import pl.debuguj.parkingspacessystem.enums.Currency;

import java.math.BigDecimal;

/**
 * Created by grzesiek on 12.10.17.
 */
public interface PaymentService {

    BigDecimal getFee(final ParkingSpace parkingSpace);

    void setCurrency(final Currency currency);

    Currency getCurrency();
}
