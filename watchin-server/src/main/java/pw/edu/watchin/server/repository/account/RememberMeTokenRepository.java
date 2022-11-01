package pw.edu.watchin.server.repository.account;

import org.springframework.data.repository.CrudRepository;
import pw.edu.watchin.server.domain.account.RememberMeTokenHash;

import java.util.List;

public interface RememberMeTokenRepository extends CrudRepository<RememberMeTokenHash, String> {

    List<RememberMeTokenHash> findAllByUsername(String username);
}
