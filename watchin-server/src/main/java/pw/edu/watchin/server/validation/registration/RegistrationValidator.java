package pw.edu.watchin.server.validation.registration;

import org.springframework.beans.factory.annotation.Autowired;
import pw.edu.watchin.server.dto.account.RegisterAccountDTO;
import pw.edu.watchin.server.repository.account.AccountRepository;
import pw.edu.watchin.server.validation.ViolationCollector;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class RegistrationValidator implements ConstraintValidator<ValidRegistration, RegisterAccountDTO> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void initialize(ValidRegistration constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegisterAccountDTO value, ConstraintValidatorContext context) {
        var collector = new ViolationCollector(context);

        if (accountRepository.existsByUsername(value.getUsername())) {
            collector.addViolation("{validation.registration.username-taken}", "username");
        }

        if (accountRepository.existsByEmail(value.getEmail())) {
            collector.addViolation("{validation.registration.email-taken}", "email");
        }

        if (!Objects.equals(value.getPassword(), value.getRepeatedPassword())) {
            collector.addViolation("{validation.registration.password-mismatch}", "repeatedPassword");
        }

        return collector.isClean();
    }
}
