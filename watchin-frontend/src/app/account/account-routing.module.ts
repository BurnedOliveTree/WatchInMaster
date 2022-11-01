import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import {UnauthenticatedGuard} from '../shared/auth/unauthenticated.guard';
import {ActivateComponent} from './activate/activate.component';
import {LogoutComponent} from './logout/logout.component';
import {AuthenticatedGuard} from '../shared/auth/authenticated.guard';
import {ForgottenPasswordComponent} from "./forgotten-password/forgotten-password.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [UnauthenticatedGuard],
    data: {
      title: 'Logowanie'
    }
  },
  {
    path: 'logout',
    component: LogoutComponent,
    canActivate: [AuthenticatedGuard]
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [UnauthenticatedGuard],
    data: {
      title: 'Rejestracja'
    }
  },
  {
    path: 'activate',
    canActivate: [UnauthenticatedGuard],
    data: {
      title: 'Aktywacja'
    },
    children: [
      {
        path: '',
        component: ActivateComponent
      },
      {
        path: ':token',
        component: ActivateComponent,
      }
    ]
  },
  {
    path: 'forgotten-password',
    component: ForgottenPasswordComponent,
    canActivate: [UnauthenticatedGuard],
    data: {
      title: 'Zapomniane hasło',
    }
  },
  {
    path: 'reset-password',
    canActivate: [UnauthenticatedGuard],
    data: {
      title: 'Nowe hasło',
    },
    children: [
      {
        path: '',
        component: ResetPasswordComponent,
      },
      {
        path: ':token',
        component: ResetPasswordComponent,
      }
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
export class AccountRoutingModule { }
