package pw.edu.watchin.server.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pw.edu.watchin.server.domain.account.AccountEntity;

import java.util.Collection;

@Getter
public class Account extends User {

    private final Integer id;
    private final String email;

    public Account(AccountEntity account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getUsername(), account.getPassword(), account.isActivated(), true, true, true, authorities);
        id = account.getId();
        email = account.getEmail();
    }
}
