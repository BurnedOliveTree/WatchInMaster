package pw.edu.watchin.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import pw.edu.watchin.server.domain.account.RememberMeTokenHash;
import pw.edu.watchin.server.repository.account.RememberMeTokenRepository;

import java.util.Date;

@Service
public class DatabaseTokenRepository implements PersistentTokenRepository {

    @Autowired
    private RememberMeTokenRepository rememberMeTokenRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        var rememberMeToken = new RememberMeTokenHash();
        rememberMeToken.setSeries(token.getSeries());
        rememberMeToken.setToken(token.getTokenValue());
        rememberMeToken.setLastUsed(token.getDate());
        rememberMeToken.setUsername(token.getUsername());
        rememberMeTokenRepository.save(rememberMeToken);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        rememberMeTokenRepository.findById(series).ifPresent(rememberMeToken -> {
            rememberMeToken.setToken(tokenValue);
            rememberMeToken.setLastUsed(lastUsed);
            rememberMeTokenRepository.save(rememberMeToken);
        });
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return rememberMeTokenRepository.findById(seriesId)
            .map(rememberMeToken -> new PersistentRememberMeToken(
                rememberMeToken.getUsername(),
                rememberMeToken.getSeries(),
                rememberMeToken.getToken(),
                rememberMeToken.getLastUsed()
            ))
            .orElse(null);
    }

    @Override
    public void removeUserTokens(String username) {
        // redis repositories do not support deleteAllBy* queries
        var tokens = rememberMeTokenRepository.findAllByUsername(username);
        rememberMeTokenRepository.deleteAll(tokens);
    }
}
