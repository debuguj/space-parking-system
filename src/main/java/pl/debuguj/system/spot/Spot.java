package pl.debuguj.system.spot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Spot implements Serializable {

    @NotNull
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$")
    private String vehiclePlate;
    @NotNull
    @DriverTypeSubSet(anyOf = {DriverType.REGULAR, DriverType.VIP})
    private DriverType driverType;
    @NotNull
    private Date beginDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Spot)) return false;
        Spot spot = (Spot) o;
        return getVehiclePlate().equals(spot.getVehiclePlate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVehiclePlate());
    }
}
