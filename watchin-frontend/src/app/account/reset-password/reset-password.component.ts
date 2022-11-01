import {Component, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {PasswordResetDTO, ValidationErrorDTO} from '../../../../generated/dto';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '../account.service';
import {assignServerValidationErrors} from '../../shared/validation/server-validation';

@Component({
  selector: 'reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent {

  @ViewChild('resetPasswordForm')
  form: NgForm;

  resetPasswordData: PasswordResetDTO = new PasswordResetDTO();

  passwordVisible: boolean = false;

  repeatedPasswordVisible: boolean = false;

  tokenError: boolean = false;

  serverError: boolean = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private accountService: AccountService) {
    this.resetPasswordData.token = this.route.snapshot.paramMap.get('token');
  }

  onSubmit() {
    this.accountService.resetPassword(this.resetPasswordData).subscribe(
      () => this.handlePasswordResetSuccess(),
      errorResponse => this.handlePasswordResetFailure(errorResponse.error)
    )
  }

  private handlePasswordResetSuccess() {
    this.router.navigateByUrl('/account/login');
  }

  private handlePasswordResetFailure(validationError?: ValidationErrorDTO) {
    const isValidationError = assignServerValidationErrors(this.form, validationError);
    this.tokenError = !isValidationError;
    this.serverError = !isValidationError && !this.tokenError;
  }
}
