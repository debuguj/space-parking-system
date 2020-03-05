package pl.debuguj.parkingspacessystem.spaces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by GB on 23.022020.
 */
@AllArgsConstructor
@Getter
public class SpaceActive implements Serializable {

    private final String vehicleRegistrationNumber;
    private final DriverType driverType;
    private final Date beginDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceActive that = (SpaceActive) o;
        return Objects.equals(vehicleRegistrationNumber, that.vehicleRegistrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleRegistrationNumber);
    }
}
