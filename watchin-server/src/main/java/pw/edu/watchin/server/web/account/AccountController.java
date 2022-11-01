package pw.edu.watchin.server.web.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pw.edu.watchin.server.dto.account.*;
import pw.edu.watchin.server.security.Account;
import pw.edu.watchin.server.security.AuthAccount;
import pw.edu.watchin.server.service.account.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/details", method = {RequestMethod.POST, RequestMethod.GET})
    public AccountDTO getDetails(@AuthAccount Account account) {
        return accountService.getAccountDetails(account);
    }

    @PostMapping("/register")
    public RegisterAccountResponseDTO register(@Valid @RequestBody RegisterAccountDTO registrationData) {
        return accountService.registerAccount(registrationData);
    }

    @PostMapping("/activate")
    public void activate(@RequestBody ActivateAccountDTO activationData) {
        accountService.activateAccount(activationData);
    }

    @PostMapping("/forgotten-password")
    public void forgottenPassword(@Valid @RequestBody ForgottenPasswordDTO forgottenPasswordData) {
        accountService.forgottenPassword(forgottenPasswordData);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody PasswordResetDTO passwordResetData) {
        accountService.resetPassword(passwordResetData);
    }
}
