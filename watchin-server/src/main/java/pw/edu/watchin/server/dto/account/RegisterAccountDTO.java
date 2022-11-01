package pw.edu.watchin.server.dto.account;

import lombok.Value;
import pw.edu.watchin.server.validation.ClassLevelValidation;
import pw.edu.watchin.server.validation.registration.ValidRegistration;

import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
@ValidRegistration(groups = ClassLevelValidation.class)
@GroupSequence({RegisterAccountDTO.class, ClassLevelValidation.class})
public class RegisterAccountDTO {

    @NotBlank(message = "{validation.common.blank}")
    String username;

    @Email(message = "{validation.common.email}")
    String email;

    @NotBlank(message = "{validation.common.blank}")
    String password;

    @NotBlank(message = "{validation.common.blank}")
    String repeatedPassword;
}
