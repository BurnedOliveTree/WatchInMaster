import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {ResponsiveLayoutService} from '../../shared/layout/responsive-layout.service';
import {PageRequest, VideoDTO} from "../../../../generated/dto";
import {ActivatedRoute, Router} from "@angular/router";
import {VideoListService} from "./video-list.service";
import {VideoService} from "./video.service";
import {AuthenticationService} from "../../shared/auth/authentication.service";
import {InfiniteScrollDirective} from "../../shared/pagination/infinite-scroll.directive";

@Component({
  selector: 'public-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.scss']
})
export class VideoComponent implements OnInit {

  @ViewChild('related')
  private related: InfiniteScrollDirective<VideoDTO, any>;

  video: VideoDTO;

  readonly relatedDataLoader = (pageRequest: PageRequest<any>) => this.videoListService.findRelated(this.video.id, pageRequest);

  constructor(private route: ActivatedRoute,
              private router: Router,
              private videoService: VideoService,
              private videoListService: VideoListService,
              private changeDetectorRef: ChangeDetectorRef,
              public authenticationService: AuthenticationService,
              public responsiveLayoutService: ResponsiveLayoutService) {
  }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.video = data.video;
      this.related?.reload().subscribe();
      this.changeDetectorRef.detectChanges();
    });
  }

  performLikeAction(like: boolean, removal: boolean) {
    this.videoService.videoLikeAction(this.video.id, {
      like: like,
      removal: removal
    }).subscribe(videoLikes => this.video.likes = videoLikes);
  }
}
