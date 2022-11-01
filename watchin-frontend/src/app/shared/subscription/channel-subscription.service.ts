import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {ChannelDTO, PageRequest, PageResponse, SubscriptionActionDTO, SubscriptionDTO} from "../../../../generated/dto";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ChannelSubscriptionService {

  constructor(private http: HttpClient) {
  }

  getSubscriptionDetails(name: string): Observable<SubscriptionDTO> {
    return this.http.get<SubscriptionDTO>(`/api/channel/${name}/subscription`);
  }

  subscriptionAction(name: string, action: SubscriptionActionDTO): Observable<SubscriptionDTO> {
    return this.http.post<SubscriptionDTO>(`/api/channel/${name}/subscribe`, action);
  }

  getSubscribedChannels(pageRequest: PageRequest<any>): Observable<PageResponse<ChannelDTO>> {
    return this.http.post<PageResponse<ChannelDTO>>('api/channels/subscribed', pageRequest);
  }
}
