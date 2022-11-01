import {NgForm} from "@angular/forms";
import {ValidationErrorDTO} from "../../../../generated/dto";

export function assignServerValidationErrors(form: NgForm, validationError?: ValidationErrorDTO): boolean {
  if (validationError?.fieldErrors) {
    validationError.fieldErrors.forEach(fieldError => form.controls[fieldError.field].setErrors({ server: fieldError.message }));
    // handled validation errors
    return true;
  }
  // not a validation error
  return false;
}
