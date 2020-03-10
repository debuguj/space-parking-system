package pl.debuguj.system.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
public class DailyIncome implements Serializable {
    public static final DailyIncome EMPTY = new DailyIncome(null, BigDecimal.ZERO);
    private Date date;
    private BigDecimal income;
}
