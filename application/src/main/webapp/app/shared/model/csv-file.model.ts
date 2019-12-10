import { Moment } from 'moment';

export const enum CsvFileState {
  UPLOADED = 'UPLOADED',
  INVALID = 'INVALID',
  CREATION_ERROR = 'CREATION_ERROR',
  COMPLETED = 'COMPLETED'
}

export interface ICsvFile {
  id?: number;
  state?: CsvFileState;
  status?: string;
  fileName?: string;
  uploadDatetime?: Moment;
  contentId?: number;
}

export class CsvFile implements ICsvFile {
  constructor(
    public id?: number,
    public state?: CsvFileState,
    public status?: string,
    public fileName?: string,
    public uploadDatetime?: Moment,
    public contentId?: number
  ) {}
}
