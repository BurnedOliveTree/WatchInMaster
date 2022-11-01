package pw.edu.watchin.server.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.edu.watchin.server.domain.account.AccountEntity;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<AccountEntity> findByUsername(String username);

    Optional<AccountEntity> findByEmail(String email);
}
