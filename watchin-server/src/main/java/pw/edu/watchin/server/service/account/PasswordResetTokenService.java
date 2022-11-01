package pw.edu.watchin.server.service.account;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.edu.watchin.common.properties.ApplicationProperties;
import pw.edu.watchin.server.domain.account.PasswordResetTokenHash;
import pw.edu.watchin.server.exception.PasswordResetTokenExpiredException;
import pw.edu.watchin.server.properties.AccountProperties;
import pw.edu.watchin.server.repository.account.PasswordResetTokenRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    @Autowired
    private AccountProperties accountProperties;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public String generateResetPasswordLink(Integer accountId) {
        var passwordResetToken = new PasswordResetTokenHash();
        passwordResetToken.setAccountId(accountId);
        passwordResetToken.setValidity(accountProperties.getPasswordReset().getTokenValidity().getSeconds());
        passwordResetTokenRepository.save(passwordResetToken);
        return buildPasswordResetLink(passwordResetToken);
    }

    @SneakyThrows(MalformedURLException.class)
    private String buildPasswordResetLink(PasswordResetTokenHash passwordResetToken) {
        var activationUrl = applicationProperties.getFrontend().getUrl()
            + applicationProperties.getDeepLinking().getPasswordReset()
            + passwordResetToken.getToken();
        return new URL(activationUrl).toString();
    }

    public Integer usePasswordResetToken(UUID tokenValue) {
        var activationToken = passwordResetTokenRepository.findById(tokenValue).orElseThrow(PasswordResetTokenExpiredException::new);
        passwordResetTokenRepository.delete(activationToken);
        return activationToken.getAccountId();
    }
}
