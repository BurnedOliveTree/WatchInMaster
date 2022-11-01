import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formattedTime'
})
export class FormattedTimePipe implements PipeTransform {

  transform(value: number): string {
    const hours = Math.floor(value / 3600);
    const minutes = Math.floor(value / 60) % 60;
    const seconds = Math.floor(value % 60);

    return [hours, minutes, seconds]
      .filter((part, index) => part > 0 || index > 0)
      .map(part => part.toString().padStart(2, '0'))
      .join(':');
  }
}
