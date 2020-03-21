package pl.debuguj.system.driver;

import javax.validation.constraints.Pattern;

public class VehicleNotExistsInDbException extends RuntimeException {
    public VehicleNotExistsInDbException(final String vehiclePlate) {
        super("Vehicle " + vehiclePlate + "not exists in database");
    }
}
