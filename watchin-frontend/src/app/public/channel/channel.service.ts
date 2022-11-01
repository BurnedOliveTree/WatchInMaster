import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ChannelDTO} from "../../../../generated/dto";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ChannelService {

  constructor(private http: HttpClient) {
  }

  getChannelDetails(name: string): Observable<ChannelDTO> {
    return this.http.get<ChannelDTO>(`/api/channel/${name}`);
  }
}
