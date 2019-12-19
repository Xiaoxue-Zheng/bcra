import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'defaultValue' })
export class DefaultValuePipe implements PipeTransform {
  transform(value: any, defaultValue: any): number {
    if (value != null) {
      return value;
    } else {
      return defaultValue;
    }
  }
}
