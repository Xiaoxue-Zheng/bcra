import { Moment } from 'moment';

export interface IParticipant {
  id?: number;
  registerDatetime?: Moment;
  lastLoginDatetime?: Moment;
  userLogin?: string;
  userId?: number;
  identifiableDataId?: number;
  procedureId?: number;
  csvFileFileName?: string;
  csvFileId?: number;
}

export class Participant implements IParticipant {
  constructor(
    public id?: number,
    public registerDatetime?: Moment,
    public lastLoginDatetime?: Moment,
    public userLogin?: string,
    public userId?: number,
    public identifiableDataId?: number,
    public procedureId?: number,
    public csvFileFileName?: string,
    public csvFileId?: number
  ) {}
}
