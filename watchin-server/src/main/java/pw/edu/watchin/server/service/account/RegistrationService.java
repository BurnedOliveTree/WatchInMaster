package pw.edu.watchin.server.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.edu.watchin.mailing.service.MailingService;
import pw.edu.watchin.server.domain.account.AccountEntity;
import pw.edu.watchin.server.dto.account.ActivateAccountDTO;
import pw.edu.watchin.server.dto.account.RegisterAccountDTO;
import pw.edu.watchin.server.dto.account.RegisterAccountResponseDTO;
import pw.edu.watchin.server.properties.AccountProperties;
import pw.edu.watchin.server.repository.account.AccountRepository;
import pw.edu.watchin.server.service.channel.ChannelManagementService;

@Service
public class RegistrationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountProperties accountProperties;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ActivationTokenService activationTokenService;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private ChannelManagementService channelManagementService;

    @Transactional
    public RegisterAccountResponseDTO registerAccount(RegisterAccountDTO registrationData) {
        var account = new AccountEntity();
        account.setUsername(registrationData.getUsername());
        account.setEmail(registrationData.getEmail());
        account.setPassword(passwordEncoder.encode(registrationData.getPassword()));
        account.setActivated(!accountProperties.getActivation().isRequired());
        accountRepository.save(account);

        var activationPending = !account.isActivated();
        if (activationPending) {
            sendRegistrationEmail(account);
        } else {
            channelManagementService.createChannel(account);
        }
        return new RegisterAccountResponseDTO(activationPending);
    }

    private void sendRegistrationEmail(AccountEntity account) {
        var activationLink = activationTokenService.generateActivationToken(account.getId());
        mailingService.sendRegistrationEmail(account.getEmail(), account.getUsername(), activationLink);
    }

    @Transactional
    public void activateAccount(ActivateAccountDTO activationData) {
        var accountId = activationTokenService.useActivationToken(activationData.getToken());
        var account = accountRepository.getOne(accountId);
        account.setActivated(true);
        channelManagementService.createChannel(account);
    }
}
