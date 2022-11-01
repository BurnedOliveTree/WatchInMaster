import {Component} from '@angular/core';
import {AuthenticationService} from '../../shared/auth/authentication.service';
import {VideoListService} from "../video/video-list.service";

@Component({
  selector: 'public-watch-later',
  templateUrl: './watch-later.component.html',
  styleUrls: ['./watch-later.component.scss']
})
export class WatchLaterComponent {

  readonly watchLaterDataLoader = this.videoListService.findWatchLater.bind(this.videoListService);

  constructor(private videoListService: VideoListService,
              public authenticationService: AuthenticationService) {
  }

}
