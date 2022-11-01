import {Component} from '@angular/core';
import {ResponsiveLayoutService} from '../../shared/layout/responsive-layout.service';

@Component({
  selector: 'public-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent {

  constructor(public responsiveLayoutService: ResponsiveLayoutService) {
  }
}
