import {Component} from '@angular/core';
import {VideoListService} from "../video/video-list.service";

@Component({
  selector: 'public-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent {

  readonly newestDataLoader = this.videoListService.findNewest.bind(this.videoListService);

  readonly mostPopularDataLoader = this.videoListService.findMostPopular.bind(this.videoListService);

  constructor(private videoListService: VideoListService) {
  }
}
