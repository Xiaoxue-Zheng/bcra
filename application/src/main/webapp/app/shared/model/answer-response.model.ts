import { IAnswerSection } from 'app/shared/model/answer-section.model';

export const enum ResponseState {
  NOT_STARTED = 'NOT_STARTED',
  IN_PROGRESS = 'IN_PROGRESS',
  SUBMITTED = 'SUBMITTED',
  INVALID = 'INVALID',
  VALIDATED = 'VALIDATED',
  FAILED = 'FAILED',
  PROCESSED = 'PROCESSED'
}

export interface IAnswerResponse {
  id?: number;
  state?: ResponseState;
  status?: string;
  questionnaireId?: number;
  answerSections?: IAnswerSection[];
}

export class AnswerResponse implements IAnswerResponse {
  constructor(
    public id?: number,
    public state?: ResponseState,
    public status?: string,
    public questionnaireId?: number,
    public answerSections?: IAnswerSection[]
  ) {}
}
