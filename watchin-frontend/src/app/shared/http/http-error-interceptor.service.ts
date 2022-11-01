import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {Router} from "@angular/router";
import {StatusCodes} from "http-status-codes";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AuthenticationService} from "../auth/authentication.service";

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router,
              private authenticationService: AuthenticationService,
              private snackBar: MatSnackBar) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError(errorResponse => {
        this.handleError(errorResponse);
        return throwError(errorResponse);
      })
    )
  }

  private handleError(errorResponse: HttpErrorResponse) {
    if ([StatusCodes.UNAUTHORIZED, StatusCodes.FORBIDDEN].some(status => errorResponse.status === status)) {
      this.authenticationService.logout().subscribe(() => this.router.navigateByUrl('/account/login'));
    } else {
      this.showSnackbar('Wystąpił nieoczekiwany błąd');
    }
  }

  private showSnackbar(message: string) {
    this.snackBar.open(message, 'Zamknij', {
      horizontalPosition: 'end',
      verticalPosition: 'top'
    });
  }
}
