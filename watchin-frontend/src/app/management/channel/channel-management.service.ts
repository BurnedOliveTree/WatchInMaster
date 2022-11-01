import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ChannelEditDTO, ResourceDTO} from "../../../../generated/dto";
import {multipartFromBase64} from "../../shared/file/base64-multipart";
import {mergeMap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ChannelManagementService {

  constructor(private http: HttpClient) {
  }

  getChannelForEdition(): Observable<ChannelEditDTO> {
    return this.http.get<ChannelEditDTO>('/api/channel/manage/edit');
  }

  saveAvatar(avatar: string): Observable<ResourceDTO> {
    return multipartFromBase64(avatar, 'avatar').pipe(
      mergeMap(payload => this.http.post<ResourceDTO>('/api/channel/manage/avatar', payload))
    );
  }

  deleteAvatar(): Observable<ResourceDTO> {
    return this.http.delete<ResourceDTO>('/api/channel/manage/avatar');
  }

  saveBackground(background: string): Observable<ResourceDTO> {
    return multipartFromBase64(background, 'background').pipe(
      mergeMap(payload => this.http.post<ResourceDTO>('/api/channel/manage/background', payload))
    );
  }

  deleteBackground(): Observable<ResourceDTO> {
    return this.http.delete<ResourceDTO>('/api/channel/manage/background');
  }

  saveDescription(description: string): Observable<string> {
    return this.http.post<string>('/api/channel/manage/description', description, {
      responseType: 'text' as 'json'
    });
  }

  deleteDescription(): Observable<string> {
    return this.http.delete<string>('/api/channel/manage/description', {
      responseType: 'text' as 'json'
    });
  }
}
