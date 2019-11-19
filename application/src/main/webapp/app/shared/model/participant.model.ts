import { Moment } from 'moment';

export interface IParticipant {
  id?: number;
  registerDatetime?: Moment;
  userLogin?: string;
  userId?: number;
}

export class Participant implements IParticipant {
  constructor(public id?: number, public registerDatetime?: Moment, public userLogin?: string, public userId?: number) {}
}
