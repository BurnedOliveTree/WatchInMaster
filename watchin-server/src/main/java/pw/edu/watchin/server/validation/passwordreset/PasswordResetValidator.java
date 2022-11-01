package pw.edu.watchin.server.validation.passwordreset;

import pw.edu.watchin.server.dto.account.PasswordResetDTO;
import pw.edu.watchin.server.validation.ViolationCollector;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordResetValidator implements ConstraintValidator<ValidPasswordReset, PasswordResetDTO> {

    @Override
    public void initialize(ValidPasswordReset constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswordResetDTO value, ConstraintValidatorContext context) {
        var collector = new ViolationCollector(context);

        if (!Objects.equals(value.getPassword(), value.getRepeatedPassword())) {
            collector.addViolation("{validation.password-reset.password-mismatch}", "repeatedPassword");
        }

        return collector.isClean();
    }
}
