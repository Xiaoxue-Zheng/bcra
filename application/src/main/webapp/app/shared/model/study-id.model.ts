import { IParticipant } from 'app/shared/model/participant.model';

export interface IStudyId {
  id?: number;
  code?: string;
  participant?: IParticipant;
}

export class StudyId implements IStudyId {
  constructor(public id?: number, public code?: string, public participant?: IParticipant) {}
}
