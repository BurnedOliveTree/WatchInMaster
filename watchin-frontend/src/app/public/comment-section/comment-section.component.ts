import {Component, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import {AuthenticationService} from '../../shared/auth/authentication.service';
import {ResponsiveLayoutService} from '../../shared/layout/responsive-layout.service';
import {VideoService} from "../video/video.service";
import {InfiniteScrollDirective} from "../../shared/pagination/infinite-scroll.directive";
import {mergeMap, tap} from "rxjs/operators";
import {BlockUI, NgBlockUI} from "ng-block-ui";
import {PageRequest, VideoCommentDTO} from "../../../../generated/dto";

@Component({
  selector: 'public-comment-section',
  templateUrl: './comment-section.component.html',
  styleUrls: ['./comment-section.component.scss']
})
export class CommentSectionComponent implements OnChanges {

  @ViewChild('comments')
  private comments: InfiniteScrollDirective<VideoCommentDTO, void>;

  @BlockUI('comment')
  private blockUI: NgBlockUI;

  @Input()
  readonly videoId: string;

  readonly commentDataLoader = (pageRequest: PageRequest<any>) => this.videoService.getVideoComments(this.videoId, pageRequest);

  constructor(public authenticationService: AuthenticationService,
              public responsiveLayoutService: ResponsiveLayoutService,
              private videoService: VideoService) {
  }

  ngOnChanges({ videoId }: SimpleChanges) {
    if (videoId && !videoId.isFirstChange()) {
      this.comments.reload().subscribe();
    }
  }

  addComment(content: string) {
    this.blockUI.start();
    this.videoService.videoCommentAction(this.videoId, { content: content }).pipe(
      mergeMap(() => this.comments.reload()),
      tap(() => this.blockUI.stop())
    ).subscribe();
  }
}
