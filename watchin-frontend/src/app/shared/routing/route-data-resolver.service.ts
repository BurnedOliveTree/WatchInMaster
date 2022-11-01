import {Injectable} from '@angular/core';
import {ActivatedRoute, Data, NavigationEnd, Router} from '@angular/router';
import {filter, map, mergeMap, tap} from 'rxjs/operators';
import {TitleService} from '../title/title.service';

@Injectable({
  providedIn: 'root'
})
export class RouteDataResolverService {

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private titleService: TitleService) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.activatedRoute),
      map(route => {
        while (route.firstChild) {
          route = route.firstChild;
        }
        return route;
      }),
      mergeMap(route => route.data),
      tap(routeData => this.handleRouteData(routeData))
    ).subscribe();
  }

  private handleRouteData(routeData: Data) {
    const title = routeData?.title;
    const titleParams = routeData?.titleParams ?? [];

    if (title instanceof Function) {
      this.titleService.setTitle(title.apply(null, titleParams.map(paramName => routeData[paramName])));
    } else if (typeof title === 'string') {
      this.titleService.setTitle(title);
    } else {
      this.titleService.setTitle();
    }
  }
}
