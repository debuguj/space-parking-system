package pl.debuguj.parkingspacessystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by grzesiek on 19.10.17.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectDateException extends Exception {

    private static final long serialVersionUID = 1L;

    public IncorrectDateException(String message) {
        super(message);
    }
}
