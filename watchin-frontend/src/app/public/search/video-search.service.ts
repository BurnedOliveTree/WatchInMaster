import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {
  PageRequest,
  PageResponse,
  VideoAutocompleteSearchResultDTO,
  VideoSearchFilterDTO,
  VideoTileDTO
} from "../../../../generated/dto";

@Injectable({
  providedIn: 'root'
})
export class VideoSearchService {

  constructor(private http: HttpClient) {
  }

  getAutocompleteOptions(pageRequest: PageRequest<VideoSearchFilterDTO>): Observable<VideoAutocompleteSearchResultDTO> {
    return this.http.post<VideoAutocompleteSearchResultDTO>('/api/videos/search/autocomplete', pageRequest);
  }

  search(pageRequest: PageRequest<VideoSearchFilterDTO>): Observable<PageResponse<VideoTileDTO>> {
    return this.http.post<PageResponse<VideoTileDTO>>('api/videos/search/filter', pageRequest);
  }
}
