import {Component, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '../account.service';
import {ForgottenPasswordDTO, ValidationErrorDTO} from '../../../../generated/dto';
import {NgForm} from '@angular/forms';
import {assignServerValidationErrors} from '../../shared/validation/server-validation';

@Component({
  selector: 'forgotten-password',
  templateUrl: './forgotten-password.component.html',
  styleUrls: ['./forgotten-password.component.scss']
})
export class ForgottenPasswordComponent {

  @ViewChild('forgottenPasswordForm')
  form: NgForm;

  forgottenPasswordData: ForgottenPasswordDTO = new ForgottenPasswordDTO();

  serverError: boolean = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private accountService: AccountService) {
  }

  onSubmit() {
    this.accountService.forgottenPassword(this.forgottenPasswordData).subscribe(
      () => this.handleForgottenPasswordSuccess(),
      errorResponse => this.handleForgottenPasswordFailure(errorResponse.error)
    )
  }

  private handleForgottenPasswordSuccess() {
    this.router.navigateByUrl('/account/reset-password');
  }

  private handleForgottenPasswordFailure(validationError?: ValidationErrorDTO) {
    this.serverError = !assignServerValidationErrors(this.form, validationError);
  }
}
