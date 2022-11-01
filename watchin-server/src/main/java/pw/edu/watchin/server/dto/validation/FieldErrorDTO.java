package pw.edu.watchin.server.dto.validation;

import lombok.Value;

@Value
public class FieldErrorDTO {
    String field;
    String message;
}
