package pl.debuguj.parkingspacessystem.domain;

import java.math.BigDecimal;

/**
 * Created by grzesiek on 07.10.17.
 */
public enum DriverType {
    REGULAR(new BigDecimal("2.0")),
    VIP(new BigDecimal("1.5"));

    private final BigDecimal factor;

    DriverType(BigDecimal factor) {
        this.factor = factor;
    }

    public BigDecimal getFactor() {
        return factor;
    }
}
