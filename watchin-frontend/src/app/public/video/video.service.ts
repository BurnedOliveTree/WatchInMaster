import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {
  PageRequest,
  PageResponse,
  VideoCommentActionDTO,
  VideoCommentDTO,
  VideoDTO, VideoFavoriteActionDTO, VideoFavoriteDTO,
  VideoLikeActionDTO,
  VideoLikesDTO, VideoWatchLaterActionDTO, VideoWatchLaterDTO
} from "../../../../generated/dto";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private http: HttpClient) {
  }

  getVideoDetails(id: string): Observable<VideoDTO> {
    return this.http.get<VideoDTO>(`/api/video/${id}`);
  }

  viewVideo(id: string): Observable<void> {
    return this.http.post<void>(`/api/video/${id}/view`, null);
  }

  videoLikeAction(id: string, action: VideoLikeActionDTO): Observable<VideoLikesDTO> {
    return this.http.post<VideoLikesDTO>(`/api/video/${id}/like`, action);
  }

  getVideoComments(id: string, pageRequest: PageRequest<any>): Observable<PageResponse<VideoCommentDTO>> {
    return this.http.post<PageResponse<VideoCommentDTO>>(`/api/video/${id}/comments`, pageRequest);
  }

  videoCommentAction(id: string, action: VideoCommentActionDTO): Observable<void> {
    return this.http.post<void>(`/api/video/${id}/comment`, action);
  }

  videoFavoriteAction(id: string, action: VideoFavoriteActionDTO): Observable<VideoFavoriteDTO> {
    return this.http.post<VideoFavoriteDTO>(`/api/video/${id}/favorite`, action);
  }

  videoWatchLaterAction(id: string, action: VideoWatchLaterActionDTO): Observable<VideoWatchLaterDTO> {
    return this.http.post<VideoWatchLaterDTO>(`/api/video/${id}/watch-later`, action);
  }
}
