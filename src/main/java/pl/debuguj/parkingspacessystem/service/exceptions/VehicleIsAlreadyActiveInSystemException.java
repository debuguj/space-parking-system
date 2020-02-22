package pl.debuguj.parkingspacessystem.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by grzesiek on 17.10.17.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VehicleIsAlreadyActiveInSystemException extends Exception {

    private static final long serialVersionUID = 1L;

    public VehicleIsAlreadyActiveInSystemException() {


    }
}