package pl.debuguj.system.operator.exceptions;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(final String plate) {
        super("Vehicle with plate " + plate + " not found");
    }
}
