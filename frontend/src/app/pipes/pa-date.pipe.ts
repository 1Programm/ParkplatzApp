import { Pipe, PipeTransform } from '@angular/core';
import { DateUtils } from '../utils/date.utils';

@Pipe({
  name: 'paDate'
})
export class ParkplatzDatePipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {

    return DateUtils.toVisibleString(value as Date);
  }

}
