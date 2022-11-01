import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FileUploadDelegate} from "./file-upload-delegate";
import {Subscription} from "rxjs";
import {map} from "rxjs/operators";
import {HttpEventType} from "@angular/common/http";

@Component({
  selector: 'file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss']
})
export class FileUploadComponent {

  constructor() {
  }

  @Input()
  readonly delegate: FileUploadDelegate<any>;

  @Output()
  readonly completed = new EventEmitter<any>();

  file: File;

  uploadSubscription: Subscription;

  uploadProgress: number = 0;

  uploadError: boolean = false;

  onFileSelected(file: File) {
    this.uploadProgress = 0;
    this.uploadError = false;
    this.file = file;
    this.initUpload();
  }

  initUpload() {
    this.uploadSubscription = this.delegate.uploadFile(this.file).pipe(
      map(event => {
        if (event.type === HttpEventType.Response) {
          this.completed.emit(event.body);
          return 100;
        }
        if (event.type === HttpEventType.UploadProgress) {
          return Math.round((100 * event.loaded) / event.total);
        }
      })
    ).subscribe(
      progress => this.uploadProgress = progress,
      () => this.uploadError = true
    );
  }

  cancelUpload() {
    this.uploadSubscription?.unsubscribe();
    this.file = null;
  }
}
