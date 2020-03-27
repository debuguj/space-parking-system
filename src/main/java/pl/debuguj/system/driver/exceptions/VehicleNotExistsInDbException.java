package pl.debuguj.system.driver.exceptions;

public class VehicleNotExistsInDbException extends RuntimeException {
    public VehicleNotExistsInDbException(final String vehiclePlate) {
        super("Vehicle " + vehiclePlate + "not exists in database");
    }
}
