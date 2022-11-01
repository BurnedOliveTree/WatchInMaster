package pw.edu.watchin.server.web;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pw.edu.watchin.server.dto.validation.FieldErrorDTO;
import pw.edu.watchin.server.dto.validation.ValidationErrorDTO;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ValidationErrorDTO handleArgumentNotValidException(BindException exception) {
        var fieldErrors = exception.getBindingResult().getFieldErrors();
        var mappedFieldErrors = fieldErrors.stream()
            .map(fieldError -> new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(Collectors.toList());
        return new ValidationErrorDTO(mappedFieldErrors);
    }
}
