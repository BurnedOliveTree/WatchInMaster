import {Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {SettingsItemType} from './settings-item-type';
import {ImageCroppedEvent} from "ngx-image-cropper";
import {BlockUI, NgBlockUI} from "ng-block-ui";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'management-settings-item',
  templateUrl: './settings-item.component.html',
  styleUrls: ['./settings-item.component.scss']
})
export class SettingsItemComponent implements OnChanges {

  @Input()
  readonly title: string;

  @Input()
  readonly description: string;

  @Input()
  readonly item: any;

  @Input()
  readonly type: SettingsItemType;

  @Input()
  readonly required: boolean = false;

  @Input()
  readonly restoreDefault: boolean = false;

  @Input()
  readonly round: boolean = false;

  @Input()
  readonly aspectRatio: number = 16 / 9;

  @Input()
  readonly options: [any, string, string][];

  @Output()
  readonly saved: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  readonly deleted: EventEmitter<void> = new EventEmitter<void>();

  @BlockUI()
  private blockUI: NgBlockUI;

  @ViewChild('imageInput', { static: false })
  private set imageInputViewChild(element: ElementRef) {
    if (element) {
      this.imageInput = element.nativeElement;
    }
  }
  private imageInput: HTMLInputElement;

  textPreview: string;

  radioPreview: any;

  imagePreview: File;

  croppedImagePreview: string;

  constructor(private snackBar: MatSnackBar) {
  }

  ngOnChanges(changes: SimpleChanges) {
    this.cancelEdit();
  }

  isEditMode(): boolean {
    switch (this.type) {
      case 'image': return this.imagePreview != null;
      case 'text': return this.textPreview != null;
      case 'radio': return this.radioPreview != null;
    }
  }

  isValid(): boolean {
    if (!this.required) {
      return true;
    }
    switch (this.type) {
      case 'image': return this.imagePreview != null;
      case 'text': return this.textPreview && this.textPreview.trim().length > 0;
      case 'radio': return true;
    }
  }

  imageSelected(file: File) {
    this.imagePreview = file;
    this.blockUI.start();
  }

  imageLoaded() {
    this.blockUI.stop();
  }

  imageCropped(event: ImageCroppedEvent) {
    this.croppedImagePreview = event.base64;
  }

  imageCropError() {
    this.blockUI.stop();
    this.cancelEdit();
    this.snackBar.open('Niepoprawny typ pliku', 'Zamknij', {
      horizontalPosition: 'end',
      verticalPosition: 'top'
    });
  }

  edit() {
    switch (this.type) {
      case 'image':
        this.imageInput.click();
        break;
      case 'text':
        this.textPreview = <string> this.item ?? '';
        break;
      case 'radio':
        this.radioPreview = this.item;
        break;
    }
  }

  cancelEdit() {
    this.textPreview = null;
    this.imagePreview = null;
    this.croppedImagePreview = null;
    this.radioPreview = null;
  }

  save() {
    switch (this.type) {
      case 'image':
        this.saved.emit(this.croppedImagePreview);
        break;
      case 'text':
        this.saved.emit(this.textPreview.trim());
        break;
      case 'radio':
        this.saved.emit(this.radioPreview);
        break;
    }
  }

  delete() {
    this.deleted.emit();
  }
}
