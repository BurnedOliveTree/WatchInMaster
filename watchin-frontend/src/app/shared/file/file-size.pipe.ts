import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fileSize'
})
export class FileSizePipe implements PipeTransform {

  transform(value: number, decimals: number = 2): string {
    if (value === 0) {
      return '0 Bytes';
    }

    const k = 1024;
    const i = Math.floor(Math.log(value) / Math.log(k));

    const size = parseFloat((value / Math.pow(k, i)).toFixed(decimals));
    const unit = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'][i];

    return `${size} ${unit}`;
  }
}
