import {Component, Input, Output, EventEmitter} from '@angular/core';
import {ChannelDTO} from "../../../../../generated/dto";
import {ChannelSubscriptionService} from "../../../shared/subscription/channel-subscription.service";
import {filter, tap} from "rxjs/operators";

@Component({
  selector: 'public-subscribed-channel',
  templateUrl: './subscribed-channel.component.html',
  styleUrls: ['./subscribed-channel.component.scss']
})
export class SubscribedChannelComponent {

  @Input()
  readonly channel: ChannelDTO;

  @Output()
  readonly unsubscribed = new EventEmitter<ChannelDTO>();

  constructor(private subscriptionService: ChannelSubscriptionService) {
  }

  unsubscribe() {
    this.subscriptionService.subscriptionAction(this.channel.name, { removal: true }).pipe(
      filter(subscription => !subscription.subscribed),
      tap(() => this.unsubscribed.next(this.channel))
    ).subscribe();
  }
}
