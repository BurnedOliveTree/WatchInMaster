import {Component, Input, Output, EventEmitter} from '@angular/core';
import {VideoItemDisplayMode} from './video-item-display-mode';
import {ResizedEvent} from 'angular-resize-event';
import {Subject} from 'rxjs';
import {VideoTileDTO} from "../../../../generated/dto";

@Component({
  selector: 'public-video-item',
  templateUrl: './video-item.component.html',
  styleUrls: ['./video-item.component.scss']
})
export class VideoItemComponent {

  private static NARROW_BREAKPOINT_PX = 600;

  @Input()
  readonly displayMode: VideoItemDisplayMode;

  @Input()
  readonly video: VideoTileDTO;

  @Output()
  readonly removedFromFavorite = new EventEmitter<VideoTileDTO>();

  @Output()
  readonly removedFromWatchLater = new EventEmitter<VideoTileDTO>();

  readonly narrow$ = new Subject<boolean>();

  constructor() {
  }

  onSizeChanged(event: ResizedEvent) {
    this.narrow$.next(event.newWidth < VideoItemComponent.NARROW_BREAKPOINT_PX);
  }
}
