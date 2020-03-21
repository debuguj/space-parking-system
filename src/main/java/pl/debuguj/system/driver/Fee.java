package pl.debuguj.system.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.debuguj.system.spot.ArchivedSpot;

import java.math.BigDecimal;
import java.util.Date;

public class Fee {
    private final String plate;
    private final Date startTime;
    private final Date stopTime;
    private final BigDecimal fee;

    public Fee(final ArchivedSpot archivedSpot) {
        this.plate = archivedSpot.getVehiclePlate();
        this.startTime = archivedSpot.getBeginDate();
        this.stopTime = archivedSpot.getFinishDate();
        this.fee = archivedSpot.getFee().get();
    }
}
