package pw.edu.watchin.server.validation.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SpringCronValidator implements ConstraintValidator<SpringCron, String> {

    @Override
    public void initialize(SpringCron constraintAnnotation) {
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null
            || Scheduled.CRON_DISABLED.equals(value)
            || CronSequenceGenerator.isValidExpression(value);
    }
}