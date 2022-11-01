import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NavigationComponent} from "./navigation/navigation.component";
import {AuthenticatedGuard} from "../shared/auth/authenticated.guard";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {VideosComponent} from "./video/videos.component";
import {ChannelEditResolver} from './channel/channel-edit.resolver';
import {VideoAddComponent} from "./video/video-add/video-add.component";
import {VideoEditComponent} from "./video/video-edit/video-edit.component";
import {ChannelComponent} from "./channel/channel.component";
import {VideoEditResolver} from "./video/video-edit/video-edit.resolver";
import {DashboardResolver} from "./dashboard/dashboard.resolver";

const routes: Routes = [
  {
    path: '',
    component: NavigationComponent,
    canActivateChild: [AuthenticatedGuard],
    children: [
      {
        path: '',
        component: DashboardComponent,
        resolve: {
          statistics: DashboardResolver
        },
        data: {
          title: 'Dashboard'
        }
      },
      {
        path: 'channel',
        component: ChannelComponent,
        resolve: {
          channel: ChannelEditResolver
        },
        data: {
          title: 'Ustawienia'
        }
      },
      {
        path: 'video',
        children: [
          {
            path: '',
            component: VideosComponent,
            data: {
              title: 'Filmy'
            }
          },
          {
            path: 'add',
            component: VideoAddComponent,
            data: {
              title: 'Dodaj nowy film'
            }
          },
          {
            path: 'edit/:id',
            component: VideoEditComponent,
            resolve: {
              video: VideoEditResolver
            },
            data: {
              title: 'Edytuj film'
            }
          },
        ]
      },
    ]
  },
  {
    path: '**',
    redirectTo: '/'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManagementRoutingModule { }
