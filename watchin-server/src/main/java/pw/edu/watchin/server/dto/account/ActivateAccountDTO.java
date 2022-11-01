package pw.edu.watchin.server.dto.account;

import lombok.Value;

import java.util.UUID;

@Value
public class ActivateAccountDTO {
    UUID token;
}
