import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {DashboardService} from "./dashboard.service";
import {StatisticsDTO} from "../../../../generated/dto";

@Injectable({
  providedIn: 'root'
})
export class DashboardResolver implements Resolve<StatisticsDTO[]> {

  constructor(private dashboardService: DashboardService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<StatisticsDTO[]> {
    return this.dashboardService.loadStatistics();
  }
}
