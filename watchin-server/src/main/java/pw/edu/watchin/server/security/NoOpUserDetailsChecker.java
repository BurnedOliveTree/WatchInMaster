package pw.edu.watchin.server.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

public class NoOpUserDetailsChecker implements UserDetailsChecker {

    @Override
    public void check(UserDetails toCheck) {
    }
}
