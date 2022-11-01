import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {VideoManagementService} from "../video-management.service";
import {VideoTableDataSource} from "./video-table-data-source";
import {MatPaginator} from "@angular/material/paginator";
import {debounceTime, distinctUntilChanged, startWith, tap} from "rxjs/operators";
import {BlockUI, NgBlockUI} from "ng-block-ui";
import {MatSort} from "@angular/material/sort";
import {fromEvent, merge} from "rxjs";
import {ResponsiveLayoutService} from "../../../shared/layout/responsive-layout.service";

@Component({
  selector: 'video-table',
  templateUrl: './video-table.component.html',
  styleUrls: ['./video-table.component.scss']
})
export class VideoTableComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator)
  private paginator: MatPaginator;

  @ViewChild(MatSort)
  private sort: MatSort;

  @ViewChild('input')
  private input: ElementRef;

  @BlockUI('video-table')
  private blockUI: NgBlockUI;

  readonly displayedColumns = ['thumbnail', 'title', 'uploaded', 'length', 'visibility', 'status', 'description', 'views'];

  readonly mobileDisplayedColumns = ['title', 'uploaded', 'length', 'visibility'];

  readonly dataSource: VideoTableDataSource;

  constructor(public responsiveLayoutService: ResponsiveLayoutService,
              videoManagementService: VideoManagementService) {
    this.dataSource = new VideoTableDataSource(videoManagementService);
  }

  ngOnInit(): void {
    this.dataSource.loading$.subscribe(loading => {
      if (loading) {
        this.blockUI.start();
      } else {
        this.blockUI.stop();
      }
    });
  }

  ngAfterViewInit() {
    fromEvent(this.input.nativeElement, 'keyup').pipe(
      debounceTime(150),
      distinctUntilChanged(),
      tap(() => {
        this.paginator.pageIndex = 0;
        this.loadPage();
      })
    ).subscribe();

    merge(this.sort.sortChange, this.paginator.page).pipe(
      startWith({}),
      tap(() => this.loadPage())
    ).subscribe();
  }

  private loadPage() {
    this.dataSource.loadPage({
      page: this.paginator.pageIndex,
      size: this.paginator.pageSize,
      filter: {
        phrase: this.input.nativeElement.value,
        sortField: this.sort.active,
        sortDirection: this.sort.direction
      }
    });
  }
}
