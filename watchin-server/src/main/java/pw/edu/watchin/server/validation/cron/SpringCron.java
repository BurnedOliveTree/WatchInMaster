package pw.edu.watchin.server.validation.cron;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SpringCronValidator.class)
public @interface SpringCron {

    String message() default "Invalid CRON expression";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
