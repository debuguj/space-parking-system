package pl.debuguj.parkingspacessystem.enums;

import java.math.BigDecimal;

/**
 * Created by grzesiek on 07.10.17.
 */
public enum DriverType {
    REGULAR(new BigDecimal("2.0"), BigDecimal.ONE),
    VIP(new BigDecimal("1.5"), BigDecimal.ZERO);

    private final BigDecimal factor;
    private final BigDecimal beginValue;

    DriverType(BigDecimal factor, BigDecimal beginValue) {
        this.factor = factor;
        this.beginValue = beginValue;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public BigDecimal getBeginValue() {
        return beginValue;
    }
}
