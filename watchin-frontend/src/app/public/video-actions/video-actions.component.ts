import {Component, Input, EventEmitter, Output} from '@angular/core';
import {VideoService} from "../video/video.service";
import {VideoFavoriteDTO, VideoWatchLaterDTO} from "../../../../generated/dto";
import {filter, map, tap} from "rxjs/operators";

@Component({
  selector: 'video-actions',
  templateUrl: './video-actions.component.html',
  styleUrls: ['./video-actions.component.scss']
})
export class VideoActionsComponent {

  @Input()
  readonly videoId: string;

  @Input()
  readonly favorite: VideoFavoriteDTO;

  @Input()
  readonly watchLater: VideoWatchLaterDTO;

  @Output()
  readonly removedFromFavorite = new EventEmitter<void>();

  @Output()
  readonly removedFromWatchLater = new EventEmitter<void>();

  constructor(private videoService: VideoService) {
  }

  favoriteAction() {
    this.videoService.videoFavoriteAction(this.videoId, { removal: this.favorite.status }).pipe(
      map(favorite => favorite.status),
      tap(status => this.favorite.status = status),
      filter(status => !status),
      tap(() => this.removedFromFavorite.next())
    ).subscribe();
  }

  watchLaterAction() {
    this.videoService.videoWatchLaterAction(this.videoId, { removal: this.watchLater.status }).pipe(
      map(watchLater => watchLater.status),
      tap(status => this.watchLater.status = status),
      filter(status => !status),
      tap(() => this.removedFromWatchLater.next())
    ).subscribe();
  }
}
