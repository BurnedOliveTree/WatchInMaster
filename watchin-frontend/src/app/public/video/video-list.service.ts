import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PageRequest, PageResponse, VideoTileDTO} from "../../../../generated/dto";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VideoListService {

  constructor(private http: HttpClient) {
  }

  findNewest(pageRequest: PageRequest<any>): Observable<PageResponse<VideoTileDTO>> {
    return this.http.post<PageResponse<VideoTileDTO>>('api/videos/newest', pageRequest);
  }

  findMostPopular(pageRequest: PageRequest<any>): Observable<PageResponse<VideoTileDTO>> {
    return this.http.post<PageResponse<VideoTileDTO>>('api/videos/popular', pageRequest);
  }

  findFavorite(pageRequest: PageRequest<any>): Observable<PageResponse<VideoTileDTO>> {
    return this.http.post<PageResponse<VideoTileDTO>>('api/videos/favorite', pageRequest);
  }

  findWatchLater(pageRequest: PageRequest<any>): Observable<PageResponse<VideoTileDTO>> {
    return this.http.post<PageResponse<VideoTileDTO>>('api/videos/watch-later', pageRequest);
  }

  findRelated(id: string, pageRequest: PageRequest<any>): Observable<PageResponse<VideoTileDTO>> {
    return this.http.post<PageResponse<VideoTileDTO>>(`api/videos/${id}/related`, pageRequest);
  }

  findFromSubscribedChannels(pageRequest: PageRequest<any>): Observable<PageResponse<VideoTileDTO>> {
    return this.http.post<PageResponse<VideoTileDTO>>('api/videos/subscribed', pageRequest);
  }
}
