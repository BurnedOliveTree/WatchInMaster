package pw.edu.watchin.server.repository.account;

import org.springframework.data.repository.CrudRepository;
import pw.edu.watchin.server.domain.account.PasswordResetTokenHash;

import java.util.UUID;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenHash, UUID> {
}
