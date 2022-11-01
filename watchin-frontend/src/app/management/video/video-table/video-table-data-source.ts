import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {PageRequest, VideoTableEntryDTO, VideoTableFilterDTO} from "../../../../../generated/dto";
import {BehaviorSubject, Observable, of, ReplaySubject} from "rxjs";
import {VideoManagementService} from "../video-management.service";
import {catchError, finalize, switchMap, tap} from "rxjs/operators";

export class VideoTableDataSource implements DataSource<VideoTableEntryDTO> {

  private dataSubject = new BehaviorSubject<VideoTableEntryDTO[]>([]);

  private countSubject = new ReplaySubject<number>(1);

  private loadingSubject = new BehaviorSubject<boolean>(false);

  readonly count$ = this.countSubject.asObservable();

  readonly loading$ = this.loadingSubject.asObservable();

  constructor(private videoManagementService: VideoManagementService) {
  }

  connect(collectionViewer: CollectionViewer): Observable<VideoTableEntryDTO[]> {
    return this.dataSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.dataSubject.complete();
    this.loadingSubject.complete();
  }

  loadPage(pageRequest: PageRequest<VideoTableFilterDTO>) {
    return of(pageRequest).pipe(
      tap(() => this.loadingSubject.next(true)),
      switchMap(pageRequest => this.videoManagementService.getAllVideos(pageRequest)),
      tap(pageResponse => this.countSubject.next(pageResponse.totalElements)),
      tap(pageResponse => this.dataSubject.next(pageResponse.content)),
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    ).subscribe();
  }
}