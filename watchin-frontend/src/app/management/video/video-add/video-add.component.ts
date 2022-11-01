import {Component} from '@angular/core';
import {ResponsiveLayoutService} from "../../../shared/layout/responsive-layout.service";
import {VideoEditDTO} from "../../../../../generated/dto";
import {ActivatedRoute} from "@angular/router";
import {MatStepper} from "@angular/material/stepper";

@Component({
  selector: 'management-video-add',
  templateUrl: './video-add.component.html',
  styleUrls: ['./video-add.component.scss']
})
export class VideoAddComponent {

  videoId: string;

  constructor(public responsiveLayoutService: ResponsiveLayoutService,
              private route: ActivatedRoute) {
  }

  videoUploaded(video: VideoEditDTO, stepper: MatStepper) {
    this.videoId = video.id;
    this.route.snapshot.data.video = video;
    setTimeout(() => stepper.next(), 0);
  }
}
