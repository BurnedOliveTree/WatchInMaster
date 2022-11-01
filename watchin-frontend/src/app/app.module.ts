import {LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SharedModule} from './shared/shared.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {HistoryAwareRouterService} from './shared/routing/history-aware-router.service';
import {RouteDataResolverService} from './shared/routing/route-data-resolver.service';
import localePl from '@angular/common/locales/pl'
import {registerLocaleData} from "@angular/common";
import {BlockUIModule} from 'ng-block-ui';
import * as moment from "moment";
import 'moment/locale/pl';
import {HttpErrorInterceptor} from "./shared/http/http-error-interceptor.service";
import {ScrollService} from "./shared/routing/scroll.service";
import {MatPaginatorIntl} from "@angular/material/paginator";
import {MatPaginatorIntlPl} from "./shared/pagination/mat-paginator-intl-pl";

// noinspection JSUnusedLocalSymbols
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    SharedModule,
    HttpClientModule,
    BlockUIModule.forRoot()
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'pl-PL' },
    { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true },
    { provide: MatPaginatorIntl, useClass: MatPaginatorIntlPl }
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
  constructor(private historyAwareRouterService: HistoryAwareRouterService,
              private routeDataResolverService: RouteDataResolverService,
              private scrollService: ScrollService) {
    registerLocaleData(localePl);
    moment.updateLocale('pl-PL', null);
  }
}
