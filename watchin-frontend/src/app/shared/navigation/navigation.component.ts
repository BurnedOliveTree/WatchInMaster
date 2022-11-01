import {Component} from '@angular/core';
import {ResponsiveLayoutService} from '../layout/responsive-layout.service';
import {AuthenticationService} from '../auth/authentication.service';

@Component({
  selector: 'navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent {

  constructor(public responsiveLayoutService: ResponsiveLayoutService,
              public authenticationService: AuthenticationService) {
  }
}
