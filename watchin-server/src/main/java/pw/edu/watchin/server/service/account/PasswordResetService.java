package pw.edu.watchin.server.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.mailing.service.MailingService;
import pw.edu.watchin.server.dto.account.ForgottenPasswordDTO;
import pw.edu.watchin.server.dto.account.PasswordResetDTO;
import pw.edu.watchin.server.exception.EntityNotFoundException;
import pw.edu.watchin.server.repository.account.AccountRepository;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void startProcess(ForgottenPasswordDTO forgottenPasswordData) {
        var account = accountRepository.findByEmail(forgottenPasswordData.getEmail()).orElseThrow(EntityNotFoundException::new);
        var passwordResetLink = passwordResetTokenService.generateResetPasswordLink(account.getId());
        mailingService.sendPasswordResetEmail(account.getEmail(), account.getUsername(), passwordResetLink);
    }

    @Transactional
    public void resetPassword(PasswordResetDTO passwordResetData) {
        var accountId = passwordResetTokenService.usePasswordResetToken(passwordResetData.getToken());
        var account = accountRepository.getOne(accountId);
        account.setPassword(passwordEncoder.encode(passwordResetData.getPassword()));
    }
}
