package pw.edu.watchin.server.dto.account;

import lombok.Value;
import pw.edu.watchin.server.validation.ClassLevelValidation;
import pw.edu.watchin.server.validation.forgottenpassword.ValidForgottenPassword;

import javax.validation.GroupSequence;
import javax.validation.constraints.Email;

@Value
@ValidForgottenPassword(groups = ClassLevelValidation.class)
@GroupSequence({ForgottenPasswordDTO.class, ClassLevelValidation.class})
public class ForgottenPasswordDTO {

    @Email(message = "{validation.common.email}")
    String email;
}
