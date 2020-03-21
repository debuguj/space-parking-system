package pl.debuguj.system.driver;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class VehicleCannotBeRegisteredInDbException extends RuntimeException {
    public VehicleCannotBeRegisteredInDbException(final String vehiclePlate) {
        super("Vehicle " + vehiclePlate + "cannot be registered in database");
    }
}
