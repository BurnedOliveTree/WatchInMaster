import {Directive, HostBinding, HostListener, Output, EventEmitter} from '@angular/core';

@Directive({
  selector: '[dragAndDrop]'
})
export class DragAndDropDirective {

  @HostBinding('class.fileHover')
  fileHover: boolean = false;

  @Output()
  fileDropped = new EventEmitter<File>();

  @HostListener('dragover', ['$event'])
  onDragOver(event: any) {
    event.preventDefault();
    event.stopPropagation();
    this.fileHover = true;
  }

  @HostListener('dragleave', ['$event'])
  public onDragLeave(event: any) {
    event.preventDefault();
    event.stopPropagation();
    this.fileHover = false;
  }

  @HostListener('drop', ['$event'])
  public onDrop(event) {
    event.preventDefault();
    event.stopPropagation();
    this.fileHover = false;
    const files = event.dataTransfer.files;
    if (files.length > 0) {
      this.fileDropped.emit(files[0]);
    }
  }
}
