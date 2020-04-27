package pl.debuguj.system.exceptions;

public class VehicleCannotBeRegisteredInDbException extends RuntimeException {
    public VehicleCannotBeRegisteredInDbException(final String vehiclePlate) {
        super("Vehicle " + vehiclePlate + "cannot be registered in database");
    }
}
