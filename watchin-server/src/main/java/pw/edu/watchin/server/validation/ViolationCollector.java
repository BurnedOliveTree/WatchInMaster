package pw.edu.watchin.server.validation;

import javax.validation.ConstraintValidatorContext;

public class ViolationCollector {

    private final ConstraintValidatorContext context;

    private boolean violationOccurred;

    public ViolationCollector(ConstraintValidatorContext context) {
        this.context = context;
        this.violationOccurred = false;
    }

    public void addViolation(String message, String propertyName) {
        violationOccurred = true;
        context.buildConstraintViolationWithTemplate(message)
            .addPropertyNode(propertyName)
            .addConstraintViolation();
    }

    public boolean isClean() {
        return !violationOccurred;
    }
}
