import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NavigationComponent} from './navigation/navigation.component';
import {ManagementRoutingModule} from "./management-routing.module";
import {SharedModule} from "../shared/shared.module";
import {MatIconModule} from "@angular/material/icon";
import {MatListModule} from "@angular/material/list";
import {DashboardComponent} from './dashboard/dashboard.component';
import {VideosComponent} from './video/videos.component';
import {MatCardModule} from "@angular/material/card";
import {LazyLoadImageModule} from "ng-lazyload-image";
import {MatButtonModule} from "@angular/material/button";
import {SettingsItemComponent} from './settings/settings-item.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {FormsModule} from '@angular/forms';
import {ImageCropperModule} from "ngx-image-cropper";
import {VideoAddComponent} from './video/video-add/video-add.component';
import {MatStepperModule} from '@angular/material/stepper';
import {VideoEditComponent} from './video/video-edit/video-edit.component';
import {VideoUploadComponent} from './video/video-add/video-upload/video-upload.component';
import {ChannelComponent} from './channel/channel.component';
import {VideoSummaryComponent} from './video/video-add/video-summary/video-summary.component';
import {MatRadioModule} from "@angular/material/radio";
import { VideoTableComponent } from './video/video-table/video-table.component';
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import {BlockUIModule} from "ng-block-ui";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

@NgModule({
  declarations: [NavigationComponent, DashboardComponent, VideosComponent, SettingsItemComponent, VideoAddComponent, VideoEditComponent, VideoUploadComponent, ChannelComponent, VideoSummaryComponent, VideoTableComponent],
  imports: [
    CommonModule,
    ManagementRoutingModule,
    SharedModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    LazyLoadImageModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    ImageCropperModule,
    MatStepperModule,
    MatRadioModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    BlockUIModule,
    MatTooltipModule,
    MatProgressSpinnerModule
  ]
})
export class ManagementModule {
}
