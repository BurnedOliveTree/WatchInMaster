import {Inject, Injectable} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {filter, tap} from "rxjs/operators";
import {DOCUMENT} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class ScrollService {

  constructor(private router: Router,
              @Inject(DOCUMENT) private document: Document) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      tap(() => this.scrollToTop())
    ).subscribe();
  }

  private getContainer(): HTMLElement {
    return this.document.querySelector('mat-sidenav-content');
  }

  scrollToTop() {
    this.getContainer()?.scroll(0, 0);
  }

  scrollUpToElement(element: HTMLElement) {
    const container = this.getContainer();
    container?.scroll(0, Math.min(element.offsetTop, container.scrollTop));
  }
}
