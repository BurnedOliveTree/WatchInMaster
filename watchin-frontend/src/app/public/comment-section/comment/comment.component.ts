import {Component, Input} from '@angular/core';
import {VideoCommentDTO} from "../../../../../generated/dto";

@Component({
  selector: 'public-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent {

  @Input()
  readonly comment: VideoCommentDTO;

  constructor() {
  }

}
