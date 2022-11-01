import {Component, Input, OnInit} from '@angular/core';
import {VideoManagementService} from "../../video-management.service";
import {mergeMap, takeWhile, tap} from "rxjs/operators";
import {VideoStatusType} from "../../../../../../generated/dto";
import {interval} from "rxjs";

@Component({
  selector: 'management-video-summary',
  templateUrl: './video-summary.component.html',
  styleUrls: ['./video-summary.component.scss']
})
export class VideoSummaryComponent implements OnInit {

  @Input()
  readonly videoId: string;

  videoStatus: VideoStatusType = VideoStatusType.PROCESSING;

  constructor(private videoManagementService: VideoManagementService) {
  }

  ngOnInit() {
    interval(2500).pipe(
      mergeMap(() => this.videoManagementService.getVideoStatus(this.videoId)),
      takeWhile(status => status === VideoStatusType.PROCESSING, true),
      tap(status => this.videoStatus = status)
    ).subscribe();
  }
}
