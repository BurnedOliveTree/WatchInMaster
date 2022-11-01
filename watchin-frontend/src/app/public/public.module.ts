import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PublicRoutingModule} from './public-routing.module';
import {HomepageComponent} from './homepage/homepage.component';
import {VideoComponent} from './video/video.component';
import {SearchComponent} from './search/search.component';
import {SharedModule} from '../shared/shared.module';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatCardModule} from '@angular/material/card';
import {MatMenuModule} from '@angular/material/menu';
import {LayoutModule} from '@angular/cdk/layout';
import {NavigationComponent} from './navigation/navigation.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {NavigationSearchComponent} from './navigation/navigation-search/navigation-search.component';
import {MatOptionModule} from '@angular/material/core';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {SubscribedComponent} from './subscribed/subscribed.component';
import {FavoriteComponent} from './favorite/favorite.component';
import {WatchLaterComponent} from './watch-later/watch-later.component';
import {VideoItemComponent} from './video-item/video-item.component';
import {VideoSectionComponent} from './video-section/video-section.component';
import {AngularResizedEventModule} from 'angular-resize-event';
import {VideoFilterComponent} from './video-filter/video-filter.component';
import {MatExpansionModule} from '@angular/material/expansion';
import {ChannelComponent} from './channel/channel.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {CommentSectionComponent} from './comment-section/comment-section.component';
import {CommentComponent} from './comment-section/comment/comment.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatTabsModule} from "@angular/material/tabs";
import {SubscribedChannelComponent} from './subscribed/subscribed-channel/subscribed-channel.component';
import {LazyLoadImageModule} from "ng-lazyload-image";
import {MomentModule} from "ngx-moment";
import {BlockUIModule} from "ng-block-ui";
import { VideoActionsComponent } from './video-actions/video-actions.component';

@NgModule({
  declarations: [
    HomepageComponent,
    VideoComponent,
    SearchComponent,
    HomepageComponent,
    NavigationComponent,
    NavigationSearchComponent,
    SubscribedComponent,
    FavoriteComponent,
    WatchLaterComponent,
    VideoItemComponent,
    VideoSectionComponent,
    VideoFilterComponent,
    ChannelComponent,
    CommentSectionComponent,
    CommentComponent,
    SubscribedChannelComponent,
    VideoActionsComponent
  ],
  imports: [
    CommonModule,
    PublicRoutingModule,
    SharedModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    LayoutModule,
    MatSidenavModule,
    MatListModule,
    MatAutocompleteModule,
    MatOptionModule,
    AngularResizedEventModule,
    MatExpansionModule,
    MatProgressBarModule,
    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule,
    MatTabsModule,
    LazyLoadImageModule,
    MomentModule,
    BlockUIModule,
  ]
})
export class PublicModule {
}
