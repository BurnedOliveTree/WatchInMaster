import { Component, EventEmitter, HostListener, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { VideoConstants } from '../video.constants';
import { Subject, timer } from 'rxjs';
import { debounceTime, filter, first, switchMap, tap } from 'rxjs/operators';

@Component({
  selector: 'video-player-controls',
  templateUrl: './video-player-controls.component.html',
  styleUrls: ['./video-player-controls.component.scss']
})
export class VideoPlayerControlsComponent implements OnInit, OnChanges {

  @Input()
  readonly playing: boolean;

  @Input()
  readonly buffering: boolean;

  @Input()
  readonly contentPosition: number;

  @Input()
  readonly contentLength: number;

  @Input()
  readonly volumeLevel: number;

  @Input()
  readonly muted: boolean;

  @Input()
  readonly buffered: [number, number][];

  @Input()
  readonly availableSources: string[];

  @Input()
  readonly selectedSource: string;

  @Input()
  readonly fullscreen: boolean;

  @Output()
  readonly playbackStateChangeRequest = new EventEmitter<boolean>();

  @Output()
  readonly volumeLevelChangeRequest = new EventEmitter<number>();

  @Output()
  readonly muteStateChangeRequest = new EventEmitter<boolean>();

  @Output()
  readonly seekRequest = new EventEmitter<number>();

  @Output()
  readonly sourceChangeRequest = new EventEmitter<string>();

  @Output()
  readonly fullscreenStateChangeRequest = new EventEmitter<boolean>();

  readonly VideoConstants = VideoConstants;

  forceControls: boolean = true;

  hovering: boolean = false;

  touching: boolean = false;

  scrubberTooltipLabel: number;

  scrubberTooltipOffset: number;

  qualityOptionsOpened: boolean = false;

  private startDetector$ = new Subject<boolean>();

  private hideTimeout$ = new Subject<boolean>();

  constructor() {
  }

  ngOnInit() {
    this.startDetector$.pipe(
      first(playing => playing),
      switchMap(() => timer(VideoConstants.CONTROLS_INITIAL_TIMEOUT_MS)),
      tap(() => this.forceControls = false)
    ).subscribe();

    this.hideTimeout$.pipe(
      debounceTime(VideoConstants.CONTROLS_HIDE_TIMEOUT_MS),
      filter(hide => hide)
    ).subscribe(() => {
      this.touching = false;
      this.hovering = false;
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.playing) {
      this.startDetector$.next(this.playing);
    }
  }

  getVolumeLevelOrMuted(): number {
    if (this.muted) {
      return 0;
    }
    return this.volumeLevel;
  }

  updateScrubberTooltip(position: number, range: number, tooltipWidth: number) {
    const adjustedPosition = Math.max(Math.min(position, range), 0);
    this.scrubberTooltipLabel = adjustedPosition / range * this.contentLength;
    this.scrubberTooltipOffset = Math.max(Math.min(adjustedPosition - tooltipWidth / 2, range - tooltipWidth), 0);
  }

  @HostListener('mouseenter', ['true'])
  @HostListener('mousemove', ['true'])
  @HostListener('mouseleave', ['false'])
  hoveringAction(hovering: boolean) {
    this.hovering = hovering;
    this.hideTimeout$.next(hovering);
  }

  touchAction(touching: boolean) {
    this.touching = touching;
    this.hideTimeout$.next(touching);
  }

  qualityOptionsAction(open: boolean) {
    this.qualityOptionsOpened = open;
  }

  playbackAnimationAction(handleElement: Element) {
    handleElement.animate([
      { opacity: 1, transform: 'scale(1)' },
      { opacity: 0, transform: 'scale(1.3)' }
    ], { duration: 500 });
  }

  requestPlaybackStateChange(playing: boolean) {
    this.playbackStateChangeRequest.emit(playing);
  }

  requestMuteStateChange(muted: boolean) {
    this.muteStateChangeRequest.emit(muted);
  }

  requestVolumeLevelChange(volume: number) {
    this.volumeLevelChangeRequest.emit(volume);
  }

  requestSeek(position: number) {
    this.seekRequest.emit(position);
  }

  requestFullscreenChangeState(fullscreen: boolean) {
    this.fullscreenStateChangeRequest.emit(fullscreen);
  }

  requestSourceChange(source: string) {
    this.sourceChangeRequest.emit(source);
  }
}
