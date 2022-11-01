import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent} from "@angular/common/http";
import {Observable} from "rxjs";
import {
  PageRequest,
  PageResponse, ResourceDTO,
  VideoEditDTO, VideoStatusType,
  VideoTableEntryDTO,
  VideoTableFilterDTO, VideoVisibilityType
} from "../../../../generated/dto";
import {multipartFromBase64} from "../../shared/file/base64-multipart";
import {mergeMap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class VideoManagementService {

  constructor(private http: HttpClient) {
  }

  uploadVideo(video: File): Observable<HttpEvent<VideoEditDTO>> {
    const payload = new FormData();
    payload.append('video', video);
    return this.http.post<VideoEditDTO>('/api/video/manage/upload', payload, {
      reportProgress: true,
      observe: 'events'
    });
  }

  getVideoForEdition(videoId: string): Observable<VideoEditDTO> {
    return this.http.get<VideoEditDTO>(`/api/video/manage/${videoId}`);
  }

  deleteVideo(videoId: string): Observable<void> {
    return this.http.delete<void>(`/api/video/manage/${videoId}`);
  }

  saveTitle(videoId: string, title: string): Observable<string> {
    return this.http.post<string>(`/api/video/manage/${videoId}/title`, title, {
      responseType: 'text' as 'json'
    });
  }

  saveDescription(videoId: string, description: string): Observable<string> {
    return this.http.post<string>(`/api/video/manage/${videoId}/description`, description, {
      responseType: 'text' as 'json'
    });
  }

  deleteDescription(videoId: string): Observable<string> {
    return this.http.delete<string>(`/api/video/manage/${videoId}/description`, {
      responseType: 'text' as 'json'
    });
  }

  saveVisibility(videoId: string, visibility: string): Observable<VideoVisibilityType> {
    return this.http.post<VideoVisibilityType>(`/api/video/manage/${videoId}/visibility`, visibility, {
      responseType: 'text' as 'json'
    });
  }

  saveThumbnail(videoId: string, background: string): Observable<ResourceDTO> {
    return multipartFromBase64(background, 'thumbnail').pipe(
      mergeMap(payload => this.http.post<ResourceDTO>(`/api/video/manage/${videoId}/thumbnail`, payload))
    );
  }

  deleteThumbnail(videoId: string): Observable<ResourceDTO> {
    return this.http.delete<ResourceDTO>(`/api/video/manage/${videoId}/thumbnail`);
  }

  getAllVideos(pageRequest: PageRequest<VideoTableFilterDTO>): Observable<PageResponse<VideoTableEntryDTO>> {
    return this.http.post<PageResponse<VideoTableEntryDTO>>('/api/video/manage/list', pageRequest);
  }

  getVideoStatus(videoId: string): Observable<VideoStatusType> {
    return this.http.get<VideoStatusType>(`/api/video/manage/${videoId}/status`);
  }
}
