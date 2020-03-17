package pl.debuguj.system.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
public class Fee {
    private final String plate;
    private final Date startTime;
    private final Date stopTime;
    private final BigDecimal fee;
}
