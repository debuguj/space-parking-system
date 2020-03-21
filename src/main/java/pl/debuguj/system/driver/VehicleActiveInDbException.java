package pl.debuguj.system.driver;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class VehicleActiveInDbException extends RuntimeException {
    public VehicleActiveInDbException(final String vehiclePlate) {
        super("Vehicle " + vehiclePlate + " is active");
    }
}
