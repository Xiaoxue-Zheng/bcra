import { IAnswerSection } from 'app/shared/model/answer-section.model';

export interface IAnswerResponse {
  id?: number;
  questionnaireId?: number;
  answerSections?: IAnswerSection[];
}

export class AnswerResponse implements IAnswerResponse {
  constructor(public id?: number, public questionnaireId?: number, public answerSections?: IAnswerSection[]) {}
}
