import {Component, Output, EventEmitter} from '@angular/core';
import {VideoManagementService} from "../../video-management.service";
import {FileUploadDelegate} from "../../../../shared/file/file-upload/file-upload-delegate";
import {Observable} from "rxjs";
import {HttpEvent} from "@angular/common/http";
import {VideoEditDTO} from "../../../../../../generated/dto";

@Component({
  selector: 'management-video-upload',
  templateUrl: './video-upload.component.html',
  styleUrls: ['./video-upload.component.scss']
})
export class VideoUploadComponent implements FileUploadDelegate<VideoEditDTO> {

  @Output()
  readonly videoUploaded = new EventEmitter<VideoEditDTO>();

  constructor(private videoManagementService: VideoManagementService) {
  }

  uploadFile(file: File): Observable<HttpEvent<VideoEditDTO>> {
    return this.videoManagementService.uploadVideo(file);
  }
}
