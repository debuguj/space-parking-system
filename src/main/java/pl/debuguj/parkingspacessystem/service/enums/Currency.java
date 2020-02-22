package pl.debuguj.parkingspacessystem.service.enums;

import java.math.BigDecimal;

/**
 * Created by grzesiek on 07.10.17.
 */
public enum Currency {

    PLN(new BigDecimal("1.0"), "Złoty"),
    USD(new BigDecimal("3.50"), "Dolar");
//    GBP(new BigDecimal("4.50"), "Funt"),
//    EUR(new BigDecimal("4.30"), "Euro");

    static {

    }

    private final BigDecimal exchangeRate;
    private final String name;

    Currency(final BigDecimal exchangeRate, final String name) {
        this.exchangeRate = exchangeRate;
        this.name = name;
    }

    public BigDecimal getExchangeRate() {

        return exchangeRate;

    }

    public String getName() {
        return name;
    }


}
