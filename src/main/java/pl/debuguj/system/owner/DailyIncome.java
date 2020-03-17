package pl.debuguj.system.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
public class DailyIncome implements Serializable {
    private final Date date;
    private final BigDecimal income;
}
