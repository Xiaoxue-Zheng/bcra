export interface ICsvContent {
  id?: number;
  dataContentType?: string;
  data?: any;
  csvFileId?: number;
}

export class CsvContent implements ICsvContent {
  constructor(public id?: number, public dataContentType?: string, public data?: any, public csvFileId?: number) {}
}
