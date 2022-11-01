import {Component} from '@angular/core';
import {AuthenticationService} from '../../shared/auth/authentication.service';
import {ChannelSubscriptionService} from "../../shared/subscription/channel-subscription.service";
import {VideoListService} from "../video/video-list.service";

@Component({
  selector: 'public-subscribed',
  templateUrl: './subscribed.component.html',
  styleUrls: ['./subscribed.component.scss']
})
export class SubscribedComponent {

  readonly channelsDataLoader = this.channelSubscriptionService.getSubscribedChannels.bind(this.channelSubscriptionService);

  readonly videosDataLoader = this.videoListService.findFromSubscribedChannels.bind(this.videoListService);

  constructor(private channelSubscriptionService: ChannelSubscriptionService,
              private videoListService: VideoListService,
              public authenticationService: AuthenticationService) {
  }
}
