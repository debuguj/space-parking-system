package pl.debuguj.parkingspacessystem.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by grzesiek on 11.10.17.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectEndDateException extends Exception {

    private static final long serialVersionUID = 1L;

    public IncorrectEndDateException() {
    }

    public IncorrectEndDateException(String message) {
        super(message);
    }

    public IncorrectEndDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
