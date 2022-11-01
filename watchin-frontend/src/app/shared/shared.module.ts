import {NgModule} from '@angular/core';
import {VideoPlayerComponent} from './video/video-player/video-player.component';
import {VideoPlayerControlsComponent} from './video/video-player/video-player-controls/video-player-controls.component';
import {CommonModule} from '@angular/common';
import {FormattedTimePipe} from './time/formatted-time.pipe';
import {FormsModule} from '@angular/forms';
import {ProgressBarComponent} from './progress-bar/progress-bar.component';
import {VideoPlayerSourceComponent} from './video/video-player/video-player-source/video-player-source.component';
import {AngularResizedEventModule} from 'angular-resize-event';
import {NavigationComponent} from './navigation/navigation.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatDividerModule} from '@angular/material/divider';
import {RouterModule} from '@angular/router';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatButtonModule} from '@angular/material/button';
import {LoginButtonComponent} from './login/login-button.component';
import {SubscribeButtonComponent} from './subscription/subscribe-button.component';
import {VarDirective} from './utils/var.directive';
import {LazyLoadImageModule} from 'ng-lazyload-image';
import {FileUploadComponent} from './file/file-upload/file-upload.component';
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {DragAndDropDirective} from './file/drag-and-drop.directive';
import {FileSizePipe} from './file/file-size.pipe';
import {InfiniteScrollDirective} from './pagination/infinite-scroll.directive';
import {CustomPluralPipe} from './i18n/custom-plural-pipe.pipe';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {LineChartComponent} from './charts/line-chart.component';
import {MatCardModule} from "@angular/material/card";
import {ChartsModule} from "ng2-charts";
import { TruncatePipe } from './labels/truncate.pipe';

@NgModule({
  declarations: [
    VideoPlayerComponent,
    VideoPlayerControlsComponent,
    FormattedTimePipe,
    ProgressBarComponent,
    VideoPlayerSourceComponent,
    NavigationComponent,
    LoginButtonComponent,
    SubscribeButtonComponent,
    VarDirective,
    FileUploadComponent,
    DragAndDropDirective,
    FileSizePipe,
    InfiniteScrollDirective,
    CustomPluralPipe,
    LineChartComponent,
    TruncatePipe
  ],
  exports: [
    VideoPlayerComponent,
    ProgressBarComponent,
    VideoPlayerSourceComponent,
    NavigationComponent,
    LoginButtonComponent,
    SubscribeButtonComponent,
    VarDirective,
    FileUploadComponent,
    FormattedTimePipe,
    InfiniteScrollDirective,
    CustomPluralPipe,
    LineChartComponent,
    TruncatePipe
  ],
  imports: [
    CommonModule,
    FormsModule,
    AngularResizedEventModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    MatDividerModule,
    RouterModule,
    MatSidenavModule,
    MatListModule,
    MatButtonModule,
    LazyLoadImageModule,
    MatProgressBarModule,
    MatSnackBarModule,
    MatCardModule,
    ChartsModule
  ]
})
export class SharedModule {
}
