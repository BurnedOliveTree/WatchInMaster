import {Injectable} from '@angular/core';
import {HttpBackend, HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SkipInterceptorsHttpClient extends HttpClient {

  constructor(handler: HttpBackend) {
    super(handler);
  }
}
