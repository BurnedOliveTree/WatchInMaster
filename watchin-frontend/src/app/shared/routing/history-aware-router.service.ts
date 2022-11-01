import {Injectable} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {filter, map, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class HistoryAwareRouterService {

  private history: string[] = [];

  constructor(private router: Router) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(event => (event as NavigationEnd).urlAfterRedirects),
      tap(url => this.history.push(url))
    ).subscribe();
  }

  navigateBack(skipRoutePatterns: string[] = []) {
    this.history.pop();
    if (skipRoutePatterns.length > 0) {
      this.navigateBackUntilAllowedRoute(skipRoutePatterns);
    }
    const destination = this.history.length > 0 ? [...this.history].pop() : '/';
    this.router.navigateByUrl(destination);
  }

  private navigateBackUntilAllowedRoute(skipRoutePatterns: string[]) {
    const route = [...this.history].pop();
    const matches = skipRoutePatterns.some(pattern => new RegExp(`^${pattern}`).test(route));
    if (matches) {
      this.history.pop();
      this.navigateBackUntilAllowedRoute(skipRoutePatterns);
    }
  }
}
