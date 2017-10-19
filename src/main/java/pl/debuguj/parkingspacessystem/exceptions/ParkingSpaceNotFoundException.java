package pl.debuguj.parkingspacessystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by grzesiek on 14.10.17.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParkingSpaceNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public ParkingSpaceNotFoundException(String message) {
        super(message);
    }
}
