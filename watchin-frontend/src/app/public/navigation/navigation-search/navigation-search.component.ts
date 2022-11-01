import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {Observable, of, Subject} from "rxjs";
import {debounceTime, distinctUntilChanged, map, switchMap} from "rxjs/operators";
import {VideoAutocompleteSearchResultDTO} from "../../../../../generated/dto";
import {VideoSearchService} from "../../search/video-search.service";

@Component({
  selector: 'public-navigation-search',
  templateUrl: './navigation-search.component.html',
  styleUrls: ['./navigation-search.component.scss']
})
export class NavigationSearchComponent implements OnInit {

  @Output()
  readonly searchAction: EventEmitter<string> = new EventEmitter<string>();

  private inputChanged$ = new Subject<string>();

  options$: Observable<VideoAutocompleteSearchResultDTO>;

  constructor(private router: Router,
              private videoSearchService: VideoSearchService) {
  }

  ngOnInit(): void {
    this.options$ = this.inputChanged$.pipe(
      debounceTime(150),
      distinctUntilChanged(),
      map(phrase => phrase.trim()),
      map(phrase => ({
        filter: {
          phrase: phrase,
          channel: null,
          date: null,
          duration: null,
          sort: null
        },
        page: 0,
        size: 10
      })),
      switchMap(pageRequest => pageRequest.filter.phrase.length > 0 ? this.videoSearchService.getAutocompleteOptions(pageRequest) : of(null))
    );
  }

  inputChanged(phrase: string) {
    this.inputChanged$.next(phrase);
  }

  search(phrase: string) {
    const query = phrase.trim();
    if (query.length === 0) {
      return;
    }
    this.searchAction.emit(query);
    this.router.navigate(['/search'], { queryParams: { query } });
  }
}
