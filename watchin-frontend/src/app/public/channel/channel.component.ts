import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ResponsiveLayoutService} from '../../shared/layout/responsive-layout.service';
import {ActivatedRoute} from '@angular/router';
import {ChannelDTO, VideoSearchFilterDTO} from '../../../../generated/dto';
import {VideoFilterComponent} from "../video-filter/video-filter.component";
import {VideoSearchService} from "../search/video-search.service";
import {ScrollService} from "../../shared/routing/scroll.service";
import {AuthenticationService} from "../../shared/auth/authentication.service";

@Component({
  selector: 'public-channel',
  templateUrl: './channel.component.html',
  styleUrls: ['./channel.component.scss']
})
export class ChannelComponent implements OnInit {

  @ViewChild(VideoFilterComponent, { static: true })
  private videoFilterComponent: VideoFilterComponent;

  @ViewChild('uploadedVideos', { static: true} )
  private uploadedVideosElement: ElementRef;

  readonly channelVideoDataLoader = this.videoSearchService.search.bind(this.videoSearchService);

  channel: ChannelDTO;

  filter: VideoSearchFilterDTO;

  constructor(private route: ActivatedRoute,
              private videoSearchService: VideoSearchService,
              private changeDetectorRef: ChangeDetectorRef,
              private scrollService: ScrollService,
              public authenticationService: AuthenticationService,
              public responsiveLayoutService: ResponsiveLayoutService) {
  }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.channel = data.channel;
      this.updateFilter(this.videoFilterComponent.filter);
    });
  }

  updateFilter(filter: VideoSearchFilterDTO) {
    this.filter = {
      ...filter,
      channel: this.channel.name
    };
    this.changeDetectorRef.detectChanges();
    this.scrollService.scrollUpToElement(this.uploadedVideosElement.nativeElement);
  }
}
