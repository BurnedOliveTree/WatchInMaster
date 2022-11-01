import {Component, OnInit} from '@angular/core';
import {StatusCodes} from 'http-status-codes';
import {HttpErrorResponse} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {AccountService} from '../account.service';

@Component({
  selector: 'account-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.scss']
})
export class ActivateComponent implements OnInit {

  activationSuccess: boolean = false;

  activationError: boolean = false;

  serverError: boolean = false;

  readonly token: string;

  constructor(private route: ActivatedRoute,
              private accountService: AccountService) {
    this.token = this.route.snapshot.paramMap.get('token');
  }

  ngOnInit() {
    if (this.token) {
      this.accountService.activate({ token: this.token }).subscribe(
        () => this.handleActivationSuccess(),
        errorResponse => this.handleActivationFailure(errorResponse)
      );
    }
  }

  private handleActivationSuccess() {
    this.activationSuccess = true;
  }

  private handleActivationFailure(errorResponse: HttpErrorResponse) {
    this.activationError = StatusCodes.BAD_REQUEST === errorResponse.status;
    this.serverError = !this.activationError;
  }
}
