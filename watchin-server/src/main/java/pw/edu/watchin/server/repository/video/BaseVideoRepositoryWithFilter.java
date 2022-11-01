package pw.edu.watchin.server.repository.video;

import org.hibernate.Session;
import org.springframework.lang.Nullable;
import pw.edu.watchin.server.security.Account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public abstract class BaseVideoRepositoryWithFilter {

    @PersistenceContext
    private EntityManager entityManager;

    protected void enableFilter(@Nullable Account account) {
        var session = entityManager.unwrap(Session.class);
        var filter = session.enableFilter("requestingChannel");
        filter.setParameter("channelId", Optional.ofNullable(account).map(Account::getId).orElse(-1)); // stupid hibernate
    }
}
