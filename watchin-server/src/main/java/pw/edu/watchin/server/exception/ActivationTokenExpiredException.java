package pw.edu.watchin.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ActivationTokenExpiredException extends ResponseStatusException {

    public ActivationTokenExpiredException() {
        super(HttpStatus.BAD_REQUEST, "Activation token expired");
    }
}
