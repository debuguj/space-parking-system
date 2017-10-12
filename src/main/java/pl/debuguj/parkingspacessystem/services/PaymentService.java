package pl.debuguj.parkingspacessystem.services;

import pl.debuguj.parkingspacessystem.domain.ParkingSpace;

import java.math.BigDecimal;

/**
 * Created by grzesiek on 12.10.17.
 */
public interface PaymentService {

    BigDecimal getFee(ParkingSpace parkingSpace);
}
