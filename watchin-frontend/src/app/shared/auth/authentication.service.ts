import {Injectable} from '@angular/core';
import {Observable, of, ReplaySubject} from 'rxjs';
import {AccountDTO} from '../../../../generated/dto';
import {HttpParams} from '@angular/common/http';
import {catchError, map, tap} from 'rxjs/operators';
import {SkipInterceptorsHttpClient} from '../http/skip-interceptors-http-client';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private currentAccountSubject: ReplaySubject<AccountDTO> = new ReplaySubject<AccountDTO>(1);

  public currentAccount: Observable<AccountDTO> = this.currentAccountSubject.asObservable();

  public isAuthenticated: Observable<boolean> = this.currentAccount.pipe(map(account => account !== null));

  constructor(private http: SkipInterceptorsHttpClient) {
    this.loadAccountDetails().subscribe();
  }

  login(username: string, password: string, rememberMe: boolean): Observable<AccountDTO> {
    const payload = new HttpParams()
      .append('username', username)
      .append('password', password)
      .append('remember-me', rememberMe.toString());
    return this.http.post<AccountDTO>('/api/account/login', payload).pipe(
      tap(account => this.currentAccountSubject.next(account))
    );
  }

  logout(): Observable<void> {
    return this.http.post<void>('/api/account/logout', null).pipe(
      tap(() => this.currentAccountSubject.next(null))
    );
  }

  loadAccountDetails(): Observable<AccountDTO> {
    return this.http.get<AccountDTO>('/api/account/details').pipe(
      catchError(() => of(null)),
      tap(account => this.currentAccountSubject.next(account))
    );
  }
}
