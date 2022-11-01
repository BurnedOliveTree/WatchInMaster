import {Component} from '@angular/core';
import {AuthenticationService} from '../../shared/auth/authentication.service';
import {VideoListService} from "../video/video-list.service";

@Component({
  selector: 'public-favorite',
  templateUrl: './favorite.component.html',
  styleUrls: ['./favorite.component.scss']
})
export class FavoriteComponent {

  readonly favoriteDataLoader = this.videoListService.findFavorite.bind(this.videoListService);

  constructor(private videoListService: VideoListService,
              public authenticationService: AuthenticationService) {
  }

}
