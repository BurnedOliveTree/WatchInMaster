package pw.edu.watchin.server.repository.account;

import org.springframework.data.repository.CrudRepository;
import pw.edu.watchin.server.domain.account.ActivationTokenHash;

import java.util.UUID;

public interface ActivationTokenRepository extends CrudRepository<ActivationTokenHash, UUID> {
}
