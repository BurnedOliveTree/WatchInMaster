import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {StatisticsDTO} from "../../../../generated/dto";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'management-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [DatePipe]
})
export class DashboardComponent {

  private readonly statistics: StatisticsDTO[] = this.route.snapshot.data.statistics;

  readonly labels = this.statistics.map(entry => this.datePipe.transform(entry.date, 'd MMMM y, HH:mm'));

  readonly subscribers = this.statistics.map(entry => entry.subscribers);

  readonly videos = this.statistics.map(entry => entry.videos);

  readonly views = this.statistics.map(entry => entry.views);

  readonly comments = this.statistics.map(entry => entry.comments);

  constructor(private route: ActivatedRoute,
              private datePipe: DatePipe) {
  }
}
