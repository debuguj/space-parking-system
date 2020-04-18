package pl.debuguj.system.exceptions;

public class VehicleActiveInDbException extends RuntimeException {
    public VehicleActiveInDbException(final String vehiclePlate) {
        super("Vehicle " + vehiclePlate + " is active");
    }
}
