package pw.edu.watchin.server.validation.forgottenpassword;

import org.springframework.beans.factory.annotation.Autowired;
import pw.edu.watchin.server.dto.account.ForgottenPasswordDTO;
import pw.edu.watchin.server.repository.account.AccountRepository;
import pw.edu.watchin.server.validation.ViolationCollector;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ForgottenPasswordValidator implements ConstraintValidator<ValidForgottenPassword, ForgottenPasswordDTO> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void initialize(ValidForgottenPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(ForgottenPasswordDTO value, ConstraintValidatorContext context) {
        var collector = new ViolationCollector(context);

        if (!accountRepository.existsByEmail(value.getEmail())) {
            collector.addViolation("{validation.forgotten-password.incorrect-email}", "email");
        }

        return collector.isClean();
    }
}
