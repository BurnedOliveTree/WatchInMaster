package pw.edu.watchin.server.service.account;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.edu.watchin.common.properties.ApplicationProperties;
import pw.edu.watchin.server.domain.account.ActivationTokenHash;
import pw.edu.watchin.server.exception.ActivationTokenExpiredException;
import pw.edu.watchin.server.properties.AccountProperties;
import pw.edu.watchin.server.repository.account.ActivationTokenRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Service
public class ActivationTokenService {

    @Autowired
    private AccountProperties accountProperties;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ActivationTokenRepository activationTokenRepository;

    public String generateActivationToken(Integer accountId) {
        var activationToken = new ActivationTokenHash();
        activationToken.setAccountId(accountId);
        activationToken.setValidity(accountProperties.getActivation().getTokenValidity().getSeconds());
        activationTokenRepository.save(activationToken);
        return buildActivationLink(activationToken);
    }

    @SneakyThrows(MalformedURLException.class)
    private String buildActivationLink(ActivationTokenHash activationToken) {
        var activationUrl = applicationProperties.getFrontend().getUrl()
            + applicationProperties.getDeepLinking().getActivation()
            + activationToken.getToken();
        return new URL(activationUrl).toString();
    }

    public Integer useActivationToken(UUID tokenValue) {
        var activationToken = activationTokenRepository.findById(tokenValue).orElseThrow(ActivationTokenExpiredException::new);
        activationTokenRepository.delete(activationToken);
        return activationToken.getAccountId();
    }
}
