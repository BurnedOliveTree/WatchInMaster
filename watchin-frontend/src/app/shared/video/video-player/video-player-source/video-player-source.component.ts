import { Component, Input } from '@angular/core';

@Component({
  selector: 'video-player-source',
  template: ''
})
export class VideoPlayerSourceComponent implements VideoPlayerSource {

  @Input()
  readonly url: string;

  @Input()
  readonly name: string;

  @Input()
  readonly default: boolean = false;

  constructor() { }
}

export interface VideoPlayerSource {
  readonly url: string;
  readonly name: string;
  readonly default: boolean;
}

export class VideoPlayerSourceList extends Array<VideoPlayerSource> {
  private readonly sourceList: VideoPlayerSource[];

  constructor(...sources: VideoPlayerSource[]) {
    super(...sources);
    this.sourceList = sources;
  }

  public names(): string[] {
    return this.sourceList.map(source => source.name);
  }
}
