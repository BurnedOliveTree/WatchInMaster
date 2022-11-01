import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {VideoEditDTO} from "../../../../../generated/dto";
import {VideoManagementService} from "../video-management.service";

@Injectable({
  providedIn: 'root'
})
export class VideoEditResolver implements Resolve<VideoEditDTO> {

  constructor(private videoManagementService: VideoManagementService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<VideoEditDTO> {
    return this.videoManagementService.getVideoForEdition(route.params.id);
  }
}
