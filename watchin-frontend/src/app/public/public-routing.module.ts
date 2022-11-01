import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {HomepageComponent} from './homepage/homepage.component';
import {VideoComponent} from './video/video.component';
import {SearchComponent} from './search/search.component';
import {NavigationComponent} from './navigation/navigation.component';
import {SubscribedComponent} from './subscribed/subscribed.component';
import {FavoriteComponent} from './favorite/favorite.component';
import {WatchLaterComponent} from './watch-later/watch-later.component';
import {SearchQueryResolver} from './search/search-query.resolver';
import {VideoResolver} from './video/video.resolver';
import {ChannelComponent} from './channel/channel.component';
import {ChannelResolver} from './channel/channel.resolver';
import {ChannelDTO, VideoDTO} from "../../../generated/dto";

const routes: Routes = [
  {
    path: '',
    component: NavigationComponent,
    children: [
      {
        path: '',
        component: HomepageComponent,
      },
      {
        path: 'subscribed',
        component: SubscribedComponent,
        data: {
          title: 'Subskrybowane'
        }
      },
      {
        path: 'favorite',
        component: FavoriteComponent,
        data: {
          title: 'Ulubione'
        }
      },
      {
        path: 'watch-later',
        component: WatchLaterComponent,
        data: {
          title: 'Do obejrzenia'
        }
      },
      {
        path: 'search',
        component: SearchComponent,
        resolve: {
          query: SearchQueryResolver
        },
        runGuardsAndResolvers: 'always',
        data: {
          title: (query) => query,
          titleParams: ['query']
        }
      },
      {
        path: 'video/:id',
        component: VideoComponent,
        resolve: {
          video: VideoResolver
        },
        data: {
          title: (video: VideoDTO) => video.title,
          titleParams: ['video']
        }
      },
      {
        path: 'channel/:name',
        component: ChannelComponent,
        resolve: {
          channel: ChannelResolver
        },
        data: {
          title: (channel: ChannelDTO) => channel.name,
          titleParams: ['channel']
        }
      }
    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule {
}
