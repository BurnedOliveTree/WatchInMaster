package pw.edu.watchin.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends ResponseStatusException {

    public EntityNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Entity not found");
    }
}
