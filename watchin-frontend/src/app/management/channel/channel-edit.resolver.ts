import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {ChannelEditDTO} from '../../../../generated/dto';
import {ChannelManagementService} from "./channel-management.service";

@Injectable({
  providedIn: 'root'
})
export class ChannelEditResolver implements Resolve<ChannelEditDTO> {

  constructor(private channelManagementService: ChannelManagementService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ChannelEditDTO> {
    return this.channelManagementService.getChannelForEdition();
  }
}
