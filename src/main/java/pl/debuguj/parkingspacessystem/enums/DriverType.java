package pl.debuguj.parkingspacessystem.enums;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by grzesiek on 07.10.17.
 */
public enum DriverType {
    REGULAR("REGULAR", new BigDecimal("2.0"), BigDecimal.ONE),
    VIP("VIP",new BigDecimal("1.5"), BigDecimal.ZERO);

    private final String value;
    private final BigDecimal factor;
    private final BigDecimal beginValue;

    DriverType(String value, BigDecimal factor, BigDecimal beginValue) {
        this.value = value;
        this.factor = factor;
        this.beginValue = beginValue;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public BigDecimal getBeginValue() {
        return beginValue;
    }

    public static DriverType fromValue(String value){
        for(DriverType dt : values()){
            if(dt.value.equalsIgnoreCase(value)){
                return dt;
            }
        }
        throw new IllegalArgumentException("Unknown enum type "+value
                +", AllowedValue are: " + Arrays.toString(values()));
    }
}
