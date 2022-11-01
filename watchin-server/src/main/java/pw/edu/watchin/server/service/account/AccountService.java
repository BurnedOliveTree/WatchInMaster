package pw.edu.watchin.server.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.edu.watchin.server.domain.account.AccountEntity;
import pw.edu.watchin.server.domain.channel.ChannelEntity;
import pw.edu.watchin.server.dto.account.*;
import pw.edu.watchin.server.repository.account.AccountRepository;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.service.resource.ResourceMapperService;

@Service
public class AccountService {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ResourceMapperService resourceMapperService;

    public AccountDTO getAccountDetails(Account account) {
        var avatar = accountRepository.findById(account.getId())
            .map(AccountEntity::getChannel)
            .map(ChannelEntity::getAvatar)
            .map(resourceMapperService::getResourceLocation)
            .orElse(null);

        return new AccountDTO(account.getEmail(), account.getUsername(), avatar);
    }

    public RegisterAccountResponseDTO registerAccount(RegisterAccountDTO registrationData) {
        return registrationService.registerAccount(registrationData);
    }

    public void activateAccount(ActivateAccountDTO activationData) {
        registrationService.activateAccount(activationData);
    }

    public void forgottenPassword(ForgottenPasswordDTO forgottenPasswordData) {
        passwordResetService.startProcess(forgottenPasswordData);
    }

    public void resetPassword(PasswordResetDTO resetPasswordData) {
        passwordResetService.resetPassword(resetPasswordData);
    }
}
