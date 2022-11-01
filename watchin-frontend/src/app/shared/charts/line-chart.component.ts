import {Component, Input} from '@angular/core';
import {ChartOptions} from "chart.js";
import {Color} from "ng2-charts";

@Component({
  selector: 'line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.scss']
})
export class LineChartComponent {

  @Input()
  readonly header: string;

  @Input()
  readonly data: number[];

  @Input()
  readonly labels: string[];

  readonly options: ChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    elements: {
      point: {
        radius: 2
      }
    },
    scales: {
      yAxes: [{
        ticks: {
          fontColor: 'white',
          precision: 0
        },
        gridLines: {
          color: 'rgba(255, 255, 255, 0.3)'
        }
      }],
      xAxes: [{
        ticks: {
          fontColor: 'white',
          autoSkip: true,
          maxTicksLimit: 1,
          maxRotation: 0,
        },
        gridLines: {
          color: 'rgba(255, 255, 255, 0.3)'
        }
      }]
    }
  };

  readonly color: Color[] = [{
    backgroundColor: 'rgba(244, 67, 54, 0.4)',
    borderColor: '#f44336',
    pointBackgroundColor: '#fff',
    pointBorderColor: '#bfbfbf',
    pointHoverBackgroundColor: '#fff'
  }];

  constructor() {
  }
}
