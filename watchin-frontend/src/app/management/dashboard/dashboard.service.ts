import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {StatisticsDTO} from "../../../../generated/dto";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) {
  }

  loadStatistics(): Observable<StatisticsDTO[]> {
    return this.http.get<StatisticsDTO[]>('/api/dashboard/statistics');
  }
}
