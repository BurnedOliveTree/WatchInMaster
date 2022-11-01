import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {VideoEditDTO, VideoVisibilityType} from "../../../../../generated/dto";
import {ActivatedRoute, Router} from "@angular/router";
import {VideoManagementService} from "../video-management.service";
import {finalize, tap} from "rxjs/operators";
import {BlockUI, NgBlockUI} from "ng-block-ui";
import {SettingsItemComponent} from "../../settings/settings-item.component";

@Component({
  selector: 'management-video-edit',
  templateUrl: './video-edit.component.html',
  styleUrls: ['./video-edit.component.scss']
})
export class VideoEditComponent implements OnInit {

  @ViewChild('title')
  private titleSettings: SettingsItemComponent;

  @ViewChild('description')
  private descriptionSettings: SettingsItemComponent;

  @ViewChild('visibility')
  private visibilitySettings: SettingsItemComponent;

  @Input()
  readonly standalone: boolean = true;

  @BlockUI()
  private blockUI: NgBlockUI;

  video: VideoEditDTO;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private videoManagementService: VideoManagementService) {
  }

  ngOnInit() {
    this.video = this.route.snapshot.data.video;
  }

  deleteVideo() {
    this.videoManagementService.deleteVideo(this.video.id).pipe(
      tap(() => this.router.navigateByUrl('/management/video'))
    ).subscribe();
  }

  changeTitle(title: string) {
    this.blockUI.start();
    this.videoManagementService.saveTitle(this.video.id, title).pipe(
      tap(response => this.video.title = response),
      tap(() => this.titleSettings.cancelEdit()),
      finalize(() => this.blockUI.stop())
    ).subscribe();
  }

  changeDescription(description?: string) {
    this.blockUI.start();
    const delegate = description
      ? this.videoManagementService.saveDescription(this.video.id, description)
      : this.videoManagementService.deleteDescription(this.video.id);
    delegate.pipe(
      tap(response => this.video.description = response),
      tap(() => this.descriptionSettings.cancelEdit()),
      finalize(() => this.blockUI.stop())
    ).subscribe();
  }

  changeVisibility(visibility: VideoVisibilityType) {
    this.blockUI.start();
    this.videoManagementService.saveVisibility(this.video.id, visibility).pipe(
      tap(response => this.video.visibility = response),
      tap(() => this.visibilitySettings.cancelEdit()),
      finalize(() => this.blockUI.stop())
    ).subscribe();
  }

  changeThumbnail(thumbnail?: string) {
    this.blockUI.start();
    const delegate = thumbnail
      ? this.videoManagementService.saveThumbnail(this.video.id, thumbnail)
      : this.videoManagementService.deleteThumbnail(this.video.id);
    delegate.pipe(
      tap(response => this.video.thumbnail = response),
      finalize(() => this.blockUI.stop())
    ).subscribe();
  }
}
