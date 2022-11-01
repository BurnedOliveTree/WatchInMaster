package pw.edu.watchin.server.dto.validation;

import lombok.Value;

import java.util.List;

@Value
public class ValidationErrorDTO {
    List<FieldErrorDTO> fieldErrors;
}
