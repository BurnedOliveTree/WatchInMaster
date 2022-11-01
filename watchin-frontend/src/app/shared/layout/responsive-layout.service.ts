import {Injectable} from '@angular/core';
import {BreakpointObserver} from '@angular/cdk/layout';
import {Observable} from 'rxjs';
import {map, shareReplay} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ResponsiveLayoutService {

  public readonly isDesktop: Observable<boolean> = this.breakpointObserver.observe('(min-width: 1024px)').pipe(
    map(result => result.matches),
    shareReplay()
  );

  public readonly isMobile: Observable<boolean> = this.isDesktop.pipe(
    map(matches => !matches)
  );

  constructor(private breakpointObserver: BreakpointObserver) {
  }
}
