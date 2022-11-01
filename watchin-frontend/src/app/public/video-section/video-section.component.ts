import {Component, EventEmitter, Input, Output} from '@angular/core';
import {VideoItemDisplayMode} from '../video-item/video-item-display-mode';
import {VideoTileDTO} from "../../../../generated/dto";

@Component({
  selector: 'public-video-section',
  templateUrl: './video-section.component.html',
  styleUrls: ['./video-section.component.scss']
})
export class VideoSectionComponent {

  @Input()
  readonly name: string;

  @Input()
  readonly displayMode: VideoItemDisplayMode;

  @Input()
  readonly videos: VideoTileDTO[];

  @Output()
  readonly removedFromFavorite = new EventEmitter<VideoTileDTO>();

  @Output()
  readonly removedFromWatchLater = new EventEmitter<VideoTileDTO>();

  constructor() {
  }
}
