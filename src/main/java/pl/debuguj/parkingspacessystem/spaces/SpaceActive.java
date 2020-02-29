package pl.debuguj.parkingspacessystem.spaces;


import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.debuguj.parkingspacessystem.spaces.enums.DriverType;
import pl.debuguj.parkingspacessystem.spaces.validation.DriverTypeSubSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by GB on 23.022020.
 */
@AllArgsConstructor
@Getter
public class SpaceActive implements Serializable {

    @NotNull
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$")
    private final String vehicleRegistrationNumber;
    @NotNull
    @DriverTypeSubSet(anyOf = {DriverType.REGULAR, DriverType.VIP})
    private final DriverType driverType;
    @NotNull
    @Past
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
