package pw.edu.watchin.server.service.security;

import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pw.edu.watchin.server.domain.account.AccountEntity;
import pw.edu.watchin.server.exception.ForbiddenException;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.Owner;

import java.util.Objects;
import java.util.Optional;

@Service
public class SecurityService {

    public boolean ensureOwnership(Object object, @Nullable Account account) {
        if (account == null) {
            throw new ForbiddenException();
        }

        var owner = Optional.ofNullable(object.getClass().getAnnotation(Owner.class))
            .map(Owner::target)
            .map(target -> PropertyAccessorFactory.forBeanPropertyAccess(object).getPropertyValue(target))
            .filter(AccountEntity.class::isInstance)
            .map(AccountEntity.class::cast)
            .orElseThrow(() -> new RuntimeException("Unable to determine owner"));

        if (!Objects.equals(owner.getId(), account.getId())) {
            throw new ForbiddenException();
        }

        return true;
    }
}
