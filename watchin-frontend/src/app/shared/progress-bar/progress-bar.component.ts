import {AfterViewInit, Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import {combineLatest, Subject} from 'rxjs';

@Component({
  selector: 'progress-bar',
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.scss']
})
export class ProgressBarComponent implements OnChanges, AfterViewInit {

  @Input()
  readonly min: number;

  @Input()
  readonly max: number;

  @Input()
  readonly ranges: [number, number][];

  @Input()
  readonly fill: string;

  @Input()
  readonly background: string = 'transparent';

  @ViewChild('canvas')
  private set canvasViewChild(element: ElementRef) {
    this.canvas = element.nativeElement;
  }
  private canvas: HTMLCanvasElement;

  private canvasLoaded$ = new Subject<void>();

  private dataChanged$ = new Subject<void>();

  private sizeChanged$ = new Subject<void>();

  constructor() {
    combineLatest([this.canvasLoaded$, this.dataChanged$]).subscribe(() => this.draw());
    combineLatest([this.canvasLoaded$, this.sizeChanged$]).subscribe(() => this.computeCanvasSize());
  }

  ngAfterViewInit() {
    this.sizeChanged$.next();
    this.canvasLoaded$.next();
  }

  ngOnChanges(changes: SimpleChanges) {
    this.dataChanged$.next();
  }

  onClientSizeChanged() {
    this.sizeChanged$.next();
  }

  private computeCanvasSize() {
    this.canvas.width = this.canvas.clientWidth;
    this.canvas.height = this.canvas.clientHeight;
    this.dataChanged$.next();
  }

  private draw() {
    const context = this.canvas.getContext('2d');
    const scale = this.canvas.width / (this.max - this.min);

    context.clearRect(0, 0, this.canvas.width, this.canvas.height);

    context.fillStyle = this.background;
    context.fillRect(0, 0, this.canvas.width, this.canvas.height);

    context.fillStyle = this.fill;
    this.ranges?.forEach(range => context.fillRect(range[0] * scale, 0, (range[1] - range[0]) * scale, this.canvas.height));
  }
}
