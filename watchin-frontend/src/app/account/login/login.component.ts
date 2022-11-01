import {Component} from '@angular/core';
import {StatusCodes} from 'http-status-codes';
import {HttpErrorResponse} from '@angular/common/http';
import {AuthenticationService} from '../../shared/auth/authentication.service';
import {HistoryAwareRouterService} from '../../shared/routing/history-aware-router.service';

@Component({
  selector: 'account-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  incorrectCredentialsError: boolean = false;

  accountNotActivatedError: boolean = false;

  serverError: boolean = false;

  passwordVisible: boolean = false;

  username: string = '';

  password: string = '';

  rememberMe: boolean = false;

  constructor(private authenticationService: AuthenticationService,
              private historyAwareRouterService: HistoryAwareRouterService) {
  }

  setPasswordVisibility(visible: boolean) {
    this.passwordVisible = visible;
  }

  onSubmit() {
    this.authenticationService.login(this.username, this.password, this.rememberMe).subscribe(
      _ => this.handleLoginSuccess(),
      errorResponse => this.handleLoginFailure(errorResponse)
    );
  }

  private handleLoginSuccess() {
    this.historyAwareRouterService.navigateBack(['/account']);
  }

  private handleLoginFailure(errorResponse: HttpErrorResponse) {
    this.incorrectCredentialsError = StatusCodes.UNAUTHORIZED === errorResponse.status;
    this.accountNotActivatedError = StatusCodes.FORBIDDEN === errorResponse.status;
    this.serverError = !this.accountNotActivatedError && !this.incorrectCredentialsError;
  }
}
