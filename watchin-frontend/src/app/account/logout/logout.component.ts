import {Component} from '@angular/core';
import {AuthenticationService} from '../../shared/auth/authentication.service';
import {catchError, tap} from 'rxjs/operators';
import {of} from 'rxjs';
import {HistoryAwareRouterService} from '../../shared/routing/history-aware-router.service';

@Component({
  selector: 'logout',
  template: ''
})
export class LogoutComponent {

  constructor(authenticationService: AuthenticationService,
              historyAwareRouterService: HistoryAwareRouterService) {
    authenticationService.logout().pipe(
      catchError(() => of(null)),
      tap(() => historyAwareRouterService.navigateBack(['/account', '/management']))
    ).subscribe();
  }
}
