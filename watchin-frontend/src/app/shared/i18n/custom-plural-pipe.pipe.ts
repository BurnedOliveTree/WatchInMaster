import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'plural'
})
export class CustomPluralPipe implements PipeTransform {

  transform(value: number, singular: string, plural: string, genitive: string): string {
    value = Math.abs(value);
    if (value === 1) {
      return singular;
    } else if (value % 10 >= 2 && value % 10 <= 4 && (value % 100 < 10 || value % 100 >= 20)) {
      return plural;
    } else {
      return genitive;
    }
  }

}
