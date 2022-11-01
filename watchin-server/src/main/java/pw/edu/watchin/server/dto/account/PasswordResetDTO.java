package pw.edu.watchin.server.dto.account;

import lombok.Value;
import pw.edu.watchin.server.validation.ClassLevelValidation;
import pw.edu.watchin.server.validation.passwordreset.ValidPasswordReset;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Value
@ValidPasswordReset(groups = ClassLevelValidation.class)
@GroupSequence({PasswordResetDTO.class, ClassLevelValidation.class})
public class PasswordResetDTO {

    UUID token;

    @NotBlank(message = "{validation.common.blank}")
    String password;

    @NotBlank(message = "{validation.common.blank}")
    String repeatedPassword;
}
