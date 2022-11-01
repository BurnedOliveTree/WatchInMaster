package pw.edu.watchin.server.dto.account;

import lombok.Value;

@Value
public class RegisterAccountResponseDTO {
    boolean activationPending;
}
