export class VideoPlayerMediaLoader {

  constructor(protected video: HTMLVideoElement) {
  }

  public playMedia(mediaUrl: string) {
    this.video.src = mediaUrl;
  }
}
