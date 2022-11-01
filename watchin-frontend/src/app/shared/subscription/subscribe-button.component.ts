import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {ChannelSubscriptionService} from "./channel-subscription.service";
import {SubscriptionDTO} from "../../../../generated/dto";

@Component({
  selector: 'subscribe-button',
  templateUrl: './subscribe-button.component.html',
  styleUrls: ['./subscribe-button.component.scss']
})
export class SubscribeButtonComponent implements OnChanges {

  @Input()
  channelName: string;

  subscription: SubscriptionDTO;

  constructor(private subscriptionService: ChannelSubscriptionService) {
  }

  ngOnChanges({ channelName }: SimpleChanges) {
    if (channelName) {
      this.subscriptionService.getSubscriptionDetails(channelName.currentValue)
        .subscribe(subscription => this.subscription = subscription);
    }
  }

  subscriptionAction() {
    this.subscriptionService.subscriptionAction(this.channelName, { removal: this.subscription.subscribed })
      .subscribe(subscription => this.subscription = subscription);
  }
}
