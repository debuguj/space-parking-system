package pl.debuguj.parkingspacessystem.spaces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class SpaceFinished implements Serializable {
    private final UUID uuid = UUID.randomUUID();
    private String vehicleRegistrationNumber;
    private DriverType driverType;
    private Date beginDate;
    private Date finishDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceFinished that = (SpaceFinished) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
