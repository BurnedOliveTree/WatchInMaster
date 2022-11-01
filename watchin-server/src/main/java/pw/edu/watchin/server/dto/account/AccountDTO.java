package pw.edu.watchin.server.dto.account;

import lombok.Value;
import pw.edu.watchin.server.dto.resource.ResourceDTO;

@Value
public class AccountDTO {
    String email;
    String username;
    ResourceDTO avatar;
}
