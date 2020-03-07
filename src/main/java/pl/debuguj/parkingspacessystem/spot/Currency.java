package pl.debuguj.parkingspacessystem.spot;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Created by GB on 27.02.2020.
 */
@Getter
public enum Currency {

    PLN(new BigDecimal("1.0"), "Złoty"),
    USD(new BigDecimal("3.50"), "Dolar");

    private final BigDecimal exchangeRate;
    private final String name;

    Currency(final BigDecimal exchangeRate, final String name) {
        this.exchangeRate = exchangeRate;
        this.name = name;
    }
}
