import { Injectable } from '@angular/core';
import {Title} from '@angular/platform-browser';

@Injectable({
  providedIn: 'root'
})
export class TitleService {

  private static APPLICATION_TITLE = 'WatchIN';

  constructor(private title: Title) {
  }

  public setTitle(newTitle?: string) {
    if (newTitle) {
      this.title.setTitle(`${newTitle} | ${TitleService.APPLICATION_TITLE}`);
    } else {
      this.title.setTitle(TitleService.APPLICATION_TITLE);
    }
  }
}
