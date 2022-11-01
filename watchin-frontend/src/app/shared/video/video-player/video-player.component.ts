import {ChangeDetectorRef, Component, ContentChildren, ElementRef, HostListener, Inject, Input, QueryList, ViewChild} from '@angular/core';
import {DOCUMENT} from '@angular/common';
import {VideoConstants} from './video.constants';
import {VideoPlayerSource, VideoPlayerSourceComponent, VideoPlayerSourceList} from './video-player-source/video-player-source.component';
import {VideoPlayerMediaLoader} from "./video-player-media-loader";
import {combineLatest, Subject} from "rxjs";

@Component({
  selector: 'video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.scss']
})
export class VideoPlayerComponent {

  @Input()
  readonly autoplay: boolean = false;

  @Input()
  readonly muted: boolean = false;

  fullscreen: boolean = false;

  videoPlaying: boolean = false;

  videoBuffering: boolean = false;

  videoContentPosition: number = 0;

  videoContentLength: number = 0;

  videoVolumeLevel: number = 0;

  videoMuted: boolean = false;

  videoBuffered: [number, number][];

  availableVideoSources: VideoPlayerSourceList;

  selectedVideoSource: VideoPlayerSource;

  private videoPlayerMediaLoader: VideoPlayerMediaLoader;

  private videoReady$ = new Subject<void>();

  private sourcesChanged$ = new Subject<void>();

  @ViewChild('video')
  private set videoViewChild(element: ElementRef) {
    this.video = element.nativeElement;
    this.videoReady$.next();
  }
  private video: HTMLVideoElement;

  @ViewChild('rootContainer')
  private set rootContainerViewChild(element: ElementRef) {
    this.container = element.nativeElement;
  }
  private container: HTMLElement;

  @ContentChildren(VideoPlayerSourceComponent)
  private set sourceContentChildren(components: QueryList<VideoPlayerSourceComponent>) {
    this.availableVideoSources = new VideoPlayerSourceList(...components);
    this.selectedVideoSource = this.availableVideoSources.find(source => source.default);
    this.sourcesChanged$.next();
  }

  constructor(@Inject(DOCUMENT) public document: Document,
              private changeDetector: ChangeDetectorRef) {
    combineLatest([this.videoReady$, this.sourcesChanged$]).subscribe(() => this.initPlayback());
  }

  onPlaybackStateChanged(playing: boolean) {
    this.videoPlaying = playing;
  }

  onContentPositionChanged(contentPosition: number) {
    this.videoContentPosition = contentPosition;
  }

  onContentLengthChanged(contentLength: number) {
    this.videoContentLength = contentLength;
  }

  onVolumeLevelChanged(volumeLevel: number, muted: boolean) {
    this.videoVolumeLevel = volumeLevel;
    this.videoMuted = muted;
  }

  onBufferingProgress(bufferingProgress: TimeRanges) {
    this.videoBuffered = Array.from(
      { length: bufferingProgress.length },
      (_, i) => [bufferingProgress.start(i), bufferingProgress.end(i)]
    );
  }

  onBufferingEvent(waiting: boolean) {
    this.videoBuffering = waiting;
  }

  private initPlayback() {
    this.cleanUp();
    if (!this.selectedVideoSource) {
      console.warn('Unable to play video due to missing source');
      return;
    }
    this.videoVolumeLevel = this.video.volume;
    this.videoMuted = this.video.muted;
    this.videoPlayerMediaLoader = new VideoPlayerMediaLoader(this.video);
    this.videoPlayerMediaLoader.playMedia(this.selectedVideoSource.url);
    if (this.autoplay) {
      this.changePlaybackState(true)
        .catch(error => console.warn(`Unable to autoplay video. ${error.message}`));
    }
    this.changeDetector.detectChanges();
  }

  private cleanUp() {
    this.fullscreen = false;
    this.videoPlaying = false;
    this.videoBuffering = false;
    this.videoContentPosition = 0;
    this.videoContentLength = 0;
    this.videoVolumeLevel = 0;
    this.videoMuted = false;
    this.videoBuffered = null;
    this.video.removeAttribute('src');
    this.video.load();
  }

  @HostListener('fullscreenchange', ['document.fullscreenElement !== null'])
  private onFullscreenStateChanged(fullscreen: boolean) {
    this.fullscreen = fullscreen;
  }

  async changePlaybackState(playing: boolean): Promise<void> {
    if (playing) {
      await this.video.play();
    } else {
      this.video.pause();
    }
  }

  changeVolumeLevel(level: number) {
    this.changeMuteState(level === VideoConstants.MIN_VOLUME);
    this.video.volume = level > VideoConstants.MIN_VOLUME ? level : VideoConstants.MAX_VOLUME;
  }

  changeMuteState(muted: boolean) {
    this.video.muted = muted;
  }

  seekTo(position: number) {
    // update value manually and let player seek to the nearest breakpoint
    this.videoContentPosition = position;
    this.video.currentTime = position;
  }

  changeSource(sourceName: string) {
    if (sourceName === this.selectedVideoSource.name) {
      return;
    }

    const currentContentPosition = this.video.currentTime;
    const currentPlaybackState = this.videoPlaying;
    this.selectedVideoSource = this.availableVideoSources.find(source => source.name === sourceName);
    this.videoPlayerMediaLoader.playMedia(this.selectedVideoSource.url);
    this.seekTo(currentContentPosition);
    this.changePlaybackState(currentPlaybackState)
      .catch(error => console.warn(error.message));
  }

  changeFullscreenState(fullscreen: boolean) {
    if (fullscreen) {
      this.container.requestFullscreen();
    } else {
      this.document.exitFullscreen();
    }
  }
}
