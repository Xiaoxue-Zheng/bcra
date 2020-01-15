import { Moment } from 'moment';

export interface IIdentifiableData {
  id?: number;
  nhsNumber?: string;
  dateOfBirth?: Moment;
  firstname?: string;
  surname?: string;
  email?: string;
  address1?: string;
  address2?: string;
  address3?: string;
  address4?: string;
  address5?: string;
  postcode?: string;
  practiceName?: string;
}

export class IdentifiableData implements IIdentifiableData {
  constructor(
    public id?: number,
    public nhsNumber?: string,
    public dateOfBirth?: Moment,
    public firstname?: string,
    public surname?: string,
    public email?: string,
    public address1?: string,
    public address2?: string,
    public address3?: string,
    public address4?: string,
    public address5?: string,
    public postcode?: string,
    public practiceName?: string
  ) {}
}
