import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {EMPTY, Observable} from 'rxjs';
import {ChannelDTO} from "../../../../generated/dto";
import {ChannelService} from "./channel.service";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ChannelResolver implements Resolve<ChannelDTO> {

  constructor(private channelService: ChannelService,
              private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ChannelDTO> {
    return this.channelService.getChannelDetails(route.params.name).pipe(
      catchError(() => {
        this.router.navigateByUrl('/');
        return EMPTY;
      })
    );
  }
}
