package pl.debuguj.system.spot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class Spot implements Serializable {

    @NotNull(message = "Vehicle plate must be provided.")
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{4,5}$", message = "Invalid email address.")
    private String vehiclePlate;
    @NotNull(message = "Driver type must be provided.")
    @DriverTypeSubSet(anyOf = {DriverType.REGULAR, DriverType.VIP})
    private DriverType driverType;
    @NotNull(message = "Begin date must be provided.")
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
