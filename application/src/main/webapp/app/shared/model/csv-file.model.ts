import { Moment } from 'moment';

export interface ICsvFile {
  id?: number;
  fileName?: string;
  uploadDatetime?: Moment;
}

export class CsvFile implements ICsvFile {
  constructor(public id?: number, public fileName?: string, public uploadDatetime?: Moment) {}
}
