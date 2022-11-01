import {
  AfterViewInit,
  Directive,
  ElementRef,
  Inject,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Renderer2,
  SimpleChanges
} from '@angular/core';
import {PageRequest, PageResponse} from "../../../../generated/dto";
import {Observable, of, Subject} from "rxjs";
import {filter, first, map, switchMap, tap} from "rxjs/operators";
import {DOCUMENT} from "@angular/common";
import {DataLoader} from "./data-loader";

@Directive({
  selector: '[infinite-scroll]',
  exportAs: 'infiniteScroll'
})
export class InfiniteScrollDirective<T, F> implements OnInit, OnDestroy, OnChanges, AfterViewInit {

  @Input()
  readonly dataLoader: DataLoader<T, F>;

  @Input()
  readonly fetchOffset = '200px';

  @Input()
  readonly prefetch = true;

  @Input()
  readonly pageSize = 10;

  @Input()
  readonly pageLimit: number;

  @Input()
  readonly filter: F;

  private pageRequest$ = new Subject<PageRequest<F>>();

  private dataUpdated$ = new Subject<void>();

  private pagedData: PageResponse<T>;

  private observer: IntersectionObserver;

  get content(): T[] {
    return this.pagedData?.content;
  }

  get currentPage(): number {
    return this.pagedData?.currentPage ?? 0;
  }

  get totalPages(): number {
    return this.pagedData?.totalPages;
  }

  get totalElements(): number {
    return this.pagedData?.totalElements;
  }

  constructor(private elementRef: ElementRef,
              private renderer: Renderer2,
              @Inject(DOCUMENT) private document: Document) {
  }

  ngOnInit() {
    this.pageRequest$.pipe(
      filter((_, index) => this.prefetch || index > 0),
      filter(pageRequest => this.totalPages == null || this.totalPages > pageRequest.page),
      filter(pageRequest => this.pageLimit == null || this.pageLimit > pageRequest.page),
      switchMap(pageRequest => this.dataLoader(pageRequest)),
      map(pageResponse => ({ ...pageResponse, content: [...this.content ?? [], ...pageResponse.content] })),
      tap(pagedData => this.pagedData = pagedData),
      tap(() => this.dataUpdated$.next())
    ).subscribe();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (Object.values(changes).some(change => !change.isFirstChange())) {
      this.reload().subscribe();
    }
  }

  ngAfterViewInit() {
    const sentinel = this.renderer.createElement('div');
    this.renderer.addClass(sentinel, 'sentinel');
    this.renderer.appendChild(this.elementRef.nativeElement, sentinel);
    setTimeout(() => this.setupIntersectionObserver(sentinel));
  }

  ngOnDestroy() {
    this.observer.disconnect();
  }

  reload(): Observable<void> {
    return of(true).pipe(
      tap(() => this.pagedData = null),
      tap(() => this.loadNextPage()),
      switchMap(() => this.dataUpdated$),
      first()
    );
  }

  remove(itemToRemove: T) {
    this.pagedData = {
      ...this.pagedData,
      content: this.content?.filter(item => item !== itemToRemove)
    };
  }

  private loadNextPage() {
    this.pageRequest$.next({
      page: this.pagedData ? this.currentPage + 1 : 0,
      size: this.pageSize,
      filter: this.filter
    });
  }

  private setupIntersectionObserver(sentinel: HTMLElement) {
    const observerOptions = {
      root: this.document.querySelector('mat-sidenav-content'),
      rootMargin: `0px 0px ${this.fetchOffset} 0px`,
      threshold: 0
    };

    this.observer = new IntersectionObserver(([entry]) => {
      if (entry.isIntersecting) {
        this.loadNextPage();
      }
    }, observerOptions);

    this.observer.observe(sentinel);
  }
}
