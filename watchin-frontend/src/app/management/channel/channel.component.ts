import {Component, OnInit, ViewChild} from '@angular/core';
import {BlockUI, NgBlockUI} from "ng-block-ui";
import {ChannelDTO} from "../../../../generated/dto";
import {ChannelManagementService} from "./channel-management.service";
import {AuthenticationService} from "../../shared/auth/authentication.service";
import {ActivatedRoute} from "@angular/router";
import {finalize, mergeMap, tap} from "rxjs/operators";
import {SettingsItemComponent} from "../settings/settings-item.component";

@Component({
  selector: 'management-channel',
  templateUrl: './channel.component.html',
  styleUrls: ['./channel.component.scss']
})
export class ChannelComponent implements OnInit {

  @ViewChild('description')
  private descriptionSettings: SettingsItemComponent;

  @BlockUI()
  private blockUI: NgBlockUI;

  channel: ChannelDTO;

  constructor(private channelManagementService: ChannelManagementService,
              private authenticationService: AuthenticationService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.data.subscribe(data => this.channel = data.channel);
  }

  changeAvatar(avatar?: string) {
    this.blockUI.start();
    const delegate = avatar
      ? this.channelManagementService.saveAvatar(avatar)
      : this.channelManagementService.deleteAvatar();
    delegate.pipe(
      tap(response => this.channel.avatar = response),
      mergeMap(() => this.authenticationService.loadAccountDetails()),
      finalize(() => this.blockUI.stop())
    ).subscribe();
  }

  changeBackground(background?: string) {
    this.blockUI.start();
    const delegate = background
      ? this.channelManagementService.saveBackground(background)
      : this.channelManagementService.deleteBackground();
    delegate.pipe(
      tap(response => this.channel.background = response),
      finalize(() => this.blockUI.stop())
    ).subscribe();
  }

  changeDescription(description?: string) {
    this.blockUI.start();
    const delegate = description
      ? this.channelManagementService.saveDescription(description)
      : this.channelManagementService.deleteDescription();
    delegate.pipe(
      tap(response => this.channel.description = response),
      tap(() => this.descriptionSettings.cancelEdit()),
      finalize(() => this.blockUI.stop())
    ).subscribe();
  }
}
