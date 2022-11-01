package pw.edu.watchin.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pw.edu.watchin.server.domain.account.AccountEntity;
import pw.edu.watchin.server.repository.account.AccountRepository;

import java.util.List;

@Primary
@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var account = findAccountEntity(username);
        return buildAccount(account);
    }

    private AccountEntity findAccountEntity(String username) {
        return accountRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Account not found"));
    }

    private Account buildAccount(AccountEntity accountEntity) {
        return new Account(accountEntity, List.of());
    }
}
