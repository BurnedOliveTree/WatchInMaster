import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {EMPTY, Observable} from 'rxjs';
import {VideoDTO} from "../../../../generated/dto";
import {catchError, tap} from "rxjs/operators";
import {VideoService} from "./video.service";

@Injectable({
  providedIn: 'root'
})
export class VideoResolver implements Resolve<VideoDTO> {

  constructor(private videoService: VideoService,
              private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<VideoDTO> {
    return this.videoService.getVideoDetails(route.params.id).pipe(
      tap(video => this.videoService.viewVideo(video.id).subscribe()),
      catchError(() => {
        this.router.navigateByUrl('/');
        return EMPTY;
      })
    );
  }
}
