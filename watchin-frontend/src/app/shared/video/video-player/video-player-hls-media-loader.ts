import Hls from "hls.js";
import {VideoPlayerMediaLoader} from "./video-player-media-loader";

export class VideoPlayerHlsMediaLoader extends VideoPlayerMediaLoader {

  private readonly hls: Hls;

  constructor(video: HTMLVideoElement) {
    super(video);
    if (Hls.isSupported()) {
      this.hls = new Hls();
      this.hls.attachMedia(this.video);
    } else if (!this.video.canPlayType('application/vnd.apple.mpegurl')) {
      console.error('Neither Media Source Extensions (MSE) API nor native HLS playback is supported!');
    }
  }

  public playMedia(mediaUrl: string) {
    if (this.hls) {
      this.hls.loadSource(mediaUrl);
    } else {
      this.video.src = mediaUrl;
    }
  }
}
