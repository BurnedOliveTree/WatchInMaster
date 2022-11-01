import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {VideoSearchService} from "./video-search.service";
import {VideoSearchFilterDTO} from "../../../../generated/dto";
import {ScrollService} from "../../shared/routing/scroll.service";
import {VideoFilterComponent} from "../video-filter/video-filter.component";

@Component({
  selector: 'public-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  @ViewChild(VideoFilterComponent, { static: true })
  private videoFilterComponent: VideoFilterComponent;

  readonly searchDataLoader = this.videoSearchService.search.bind(this.videoSearchService);

  filter: VideoSearchFilterDTO;

  private query: string;

  get header(): string {
    return `Wyniki wyszukiwania dla frazy '${this.query}'`;
  }

  constructor(private route: ActivatedRoute,
              private videoSearchService: VideoSearchService,
              private changeDetectorRef: ChangeDetectorRef,
              private scrollService: ScrollService) {
  }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.query = data.query;
      this.updateFilter(this.videoFilterComponent.filter);
    });
  }

  updateFilter(filter: VideoSearchFilterDTO) {
    this.filter = {
      ...filter,
      phrase: this.query,
    };
    this.changeDetectorRef.detectChanges();
    this.scrollService.scrollToTop();
  }
}
