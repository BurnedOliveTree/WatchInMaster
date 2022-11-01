import {Component, ViewChild} from '@angular/core';
import {AccountService} from '../account.service';
import {Router} from '@angular/router';
import {NgForm} from '@angular/forms';
import {RegisterAccountDTO, RegisterAccountResponseDTO, ValidationErrorDTO} from '../../../../generated/dto';
import {assignServerValidationErrors} from '../../shared/validation/server-validation';

@Component({
  selector: 'account-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  @ViewChild('registerForm')
  form: NgForm;

  passwordVisible: boolean = false;

  repeatedPasswordVisible: boolean = false;

  registrationData: RegisterAccountDTO = new RegisterAccountDTO();

  serverError: boolean = false;

  constructor(private accountService: AccountService,
              private router: Router) {
  }

  onSubmit() {
    this.accountService.register(this.registrationData).subscribe(
      response => this.handleRegistrationSuccess(response),
      errorResponse => this.handleRegistrationFailure(errorResponse.error)
    );
  }

  private handleRegistrationSuccess(response: RegisterAccountResponseDTO) {
    if (response.activationPending) {
      this.router.navigateByUrl('/account/activate');
    } else {
      this.router.navigateByUrl('/account/login');
    }
  }

  private handleRegistrationFailure(validationError?: ValidationErrorDTO) {
    this.serverError = !assignServerValidationErrors(this.form, validationError);
  }
}
