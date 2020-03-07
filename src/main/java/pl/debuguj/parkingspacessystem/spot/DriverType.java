package pl.debuguj.parkingspacessystem.spot;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Created by GB on 27.02.2020.
 */
@Getter
public enum DriverType {
    REGULAR("REGULAR", new BigDecimal("2.0"), BigDecimal.ONE),
    VIP("VIP", new BigDecimal("1.5"), BigDecimal.ZERO);

    private final String value;
    private final BigDecimal factor;
    private final BigDecimal beginValue;

    DriverType(final String value, final BigDecimal factor, final BigDecimal beginValue) {
        this.value = value;
        this.factor = factor;
        this.beginValue = beginValue;
    }

}
