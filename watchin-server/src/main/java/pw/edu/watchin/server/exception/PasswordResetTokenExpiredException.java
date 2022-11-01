package pw.edu.watchin.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordResetTokenExpiredException extends ResponseStatusException {

    public PasswordResetTokenExpiredException() {
        super(HttpStatus.BAD_REQUEST, "Password reset token expired");
    }
}
