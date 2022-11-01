import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {
  ActivateAccountDTO,
  ForgottenPasswordDTO,
  PasswordResetDTO,
  RegisterAccountDTO,
  RegisterAccountResponseDTO
} from '../../../generated/dto';
import {SkipInterceptorsHttpClient} from "../shared/http/skip-interceptors-http-client";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: SkipInterceptorsHttpClient) {
  }

  register(registrationData: RegisterAccountDTO): Observable<RegisterAccountResponseDTO> {
    return this.http.post<RegisterAccountResponseDTO>('/api/account/register', registrationData);
  }

  activate(activationData: ActivateAccountDTO): Observable<void> {
    return this.http.post<void>('/api/account/activate', activationData);
  }

  forgottenPassword(forgottenPasswordData: ForgottenPasswordDTO): Observable<void> {
    return this.http.post<void>('/api/account/forgotten-password', forgottenPasswordData);
  }

  resetPassword(resetPasswordData: PasswordResetDTO): Observable<void> {
    return this.http.post<void>('/api/account/reset-password', resetPasswordData);
  }
}
