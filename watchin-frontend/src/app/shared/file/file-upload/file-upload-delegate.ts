import {Observable} from "rxjs";
import {HttpEvent} from "@angular/common/http";

export interface FileUploadDelegate<T> {
  uploadFile(file: File): Observable<HttpEvent<T>>
}
