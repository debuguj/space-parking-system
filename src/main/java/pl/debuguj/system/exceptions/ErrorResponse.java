package pl.debuguj.system.exceptions;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
}
